package com.ljc.review.common.io.z_netty.complexpool;

import com.ljc.review.common.io.z_netty.complexpool.util.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 连接池的实现
 * 1.获取连接
 * 2.归还连接
 * 3.动态回收连接
 * 4.心跳检测
 * 5.考虑微服务
 */
public class NettyClientPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientPool.class);
    //单例，pool使用volatile修饰
    private static volatile NettyClientPool pool;
    //key为目标主机的InetSocketAddress对象，value为对应的连接池
    private static Map<InetSocketAddress, FixedChannelPool> pools = new HashMap<>(); //记录服务器和连接池的对应关系
    //构建工具
    private final EventLoopGroup group = new NioEventLoopGroup();
    private final Bootstrap strap = new Bootstrap();
    //服务器地址抽象集合
    private static List<InetSocketAddress> addressList;

    //单例方法
    public static NettyClientPool getInstance() {
        if (pool == null) {
            synchronized (NettyClientPool.class) {
                if (pool == null) {
                    pool = new NettyClientPool();
                }
            }
        }
        return pool;
    }

    //构造方法
    private NettyClientPool() {
        build();
    }

    /**
     * 构建连接池
     */
    private void build() {
        //连接参数设置
        strap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        //连接池创建工具 ChannelPoolMap是Netty定义的辅助接口，和Map无继承关系
        ChannelPoolMap<InetSocketAddress, FixedChannelPool> poolMap = new AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(strap.remoteAddress(key), new NettyChannelPoolHandler(), Constant.MAX_CONNECTIONS);
            }
        };
        //通过poolMap为每一个服务器创建一个连接池
        String remoteAddress = "127.0.0.1:8180";
        getInetSocketAddress(remoteAddress);
        for (InetSocketAddress address : addressList) {
            pools.put(address, poolMap.get(address));
        }
    }

    //对外接口
    public Channel getChannel(long random) {
        return getChannel(random, 0);
    }

    /**
     * 根据随机数(时间戳)取出随机的server对应pool，从pool中取出channel
     * pool.acquiredChannelCount() 对应池中的channel数目
     * 连接池动态扩容：      初始化时指定了最大连接数，如果连接池队列中取不到channel，会自动创建channel。默认使用FIFO的获取方式：回收的channel优先被再次get到
     * SERVER宕机自动切换:  指定重试次数，get()发生连接异常，则对随机数+1，从下一个池中重新获取,
     * <p>
     * 后期如有必要可优化为：Server注册到注册中心，从注册中心获取连接池对应的address，或者注册到zookeeper中，都需要单独写实现
     */
    private Channel getChannel(long random, int retry) {
        Channel channel = null;
        try {
            //按时间戳取余指定一个服务器，获取对应的连接池
            long poolIndex = random % addressList.size();
            InetSocketAddress socketAddress = addressList.get((int) poolIndex);
            FixedChannelPool pool = pools.get(socketAddress);
            //从池中获取一个连接健康的连接(或新建)
            Future<Channel> future = pool.acquire();
            channel = future.get();
            //将random关联到当前channel以便后续释放连接
            AttributeKey<Long> randomID = AttributeKey.valueOf(Constant.RANDOM_KEY);
            channel.attr(randomID).set(random);
        } catch (ExecutionException e) {
            e.printStackTrace();
            //换连接池尝试，每个服务器尝试获取取2次
            if (retry < addressList.size() * 2) {
                return getChannel(++random, ++retry);
            } else {
                LOGGER.error("没有获取到可以连接的server，server list [{}]", addressList);
                throw new RuntimeException("没有获取到可以连接的server");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 回收channel进池，需要保证随机值和getChannel获取到的随机值是同一个，才能从同一个pool中释放资源
     */
    public static void release(Channel ch) {
        //获取对应的服务器
        long random = ch.attr(AttributeKey.<Long>valueOf(Constant.RANDOM_KEY)).get();
        ch.flush();
        long poolIndex = random % addressList.size();
        //获取对应的连接池后释放连接
        pools.get(addressList.get((int) poolIndex)).release(ch);
    }

    /**
     * 获取线程池的hash值
     */
    public static int getPoolHash(Channel ch) {
        long random = ch.attr(AttributeKey.<Long>valueOf(Constant.RANDOM_KEY)).get();
        long poolIndex = random % pools.size();
        return System.identityHashCode(pools.get(addressList.get((int) poolIndex)));
    }

    /**
     * 将参数解析为服务器地址列表
     */
    private void getInetSocketAddress(String addresses) {
        addressList = new ArrayList<>(4);
        if (StringUtils.isEmpty(addresses)) {
            throw new IllegalArgumentException("address is empty!");
        }
        String[] addressArr = addresses.split(",");
        for (String address : addressArr) {
            String[] split = address.split(":");
            if (split.length != 2) {
                throw new IllegalArgumentException("address不符合IP:PORT格式！");
            }
            addressList.add(new InetSocketAddress(split[0], Integer.parseInt(split[1])));
        }
    }

}

















