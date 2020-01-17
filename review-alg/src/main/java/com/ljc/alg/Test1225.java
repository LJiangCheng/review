package com.ljc.alg;

import org.junit.Test;

public class Test1225 {
    static int i = 1;

    @Test
    public void print() throws Exception {
        final Object lock = new Object();
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    synchronized (lock) {
                        if (i % 2 == 0) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(i);
                        i++;
                        lock.notifyAll();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    synchronized (lock) {
                        if (i % 2 != 0) {
                            //如果不是偶数，等待，释放锁
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //如果是偶数，工作，完成之后唤醒其他线程
                        System.out.println(i);
                        i++;
                        lock.notifyAll();
                    }
                }
            }
        }).start();

    }
}
/*1.用java写一款类似于redis的key/value方式的内存数据库，要考虑并发读写，考虑数据过期策略

2.有n个赛车，让它们都在起跑线上就绪后，同时出发，用Java多线程的技术把这种情况写出来
public void carsRun(int n){
	final Object lockObj = new Object();  //
	//初始化n个线程
    for(int i=0;i<n;i++){
    	Thread thread = new Thread(new Car(lockObj));
		thread.start();
    }
    //唤醒所有线程
 	lockObj.notifyAll();
}

class Car implements Runnable{
	private Object lock;
    public Car(Object lock){
    	this.lock = lock;
    }
	@Overriod
    public void run(){
    	lock.wait();
		forward();
    }

	private void forward(){
    	//向前跑
        System.out.println("出发啦...")
    }

}


3.题目说明

有一个字符串它的构成是词+空格的组合，如“北京 杭州 杭州 北京”， 要求输入一个匹配模式（简单的以字符来写）， 比如 aabb, 来判断该字符串是否符合该模式， 举个例子：
  1.  pattern = "abba", str="北京 杭州 杭州 北京" 返回 ture
  2.  pattern = "aabb", str="北京 杭州 杭州 北京" 返回 false
  3.  pattern = "abc", str="北京 杭州 杭州 南京" 返回 false
  4.  pattern = "acac", str="北京 杭州 北京 广州" 返回 false
public class Solution {
  public boolean wordPattern(String pattern, String str) {
    	if(pattern != null && str != null){
              String[] arr1 =  pattern.split("");
              String[] arr2 = str.split(" ");
              //首先判断各自的数组长度
              if(arr1.length != arr2.length){
                  return false;
              }
          	  //使用Map记录模式
          	  Map<String,Set<Integer>> patternMap = new LinkedHashMap<>();
              for(int i=0;i<arr1.length;i++){
              		String s = arr1[i];
                	if(patternMap.containsKey(s)){
                    	patternMap.get(s).add(i);
                    } else{
                    	Set<Integer> set = new HashSet<>();
                        set.add(i);
                        patternMap.put(s,set);
                    }
             }
          	//记录str
           Map<String,Set<Integer>> strMap = new LinkedHashMap<>();
          	for(int i=0;i<arr2.length;i++){
              		String s = arr2[i];
                	if(strMap.containsKey(s)){
                    	strMap.get(s).add(i);
                    } else{
                    	Set<Integer> set = new HashSet<>();
                        set.add(i);
                        strMap.put(s,set);
                    }
             }
           //比对
           if(patternMap.size() != strMap.size()){
           		return false;
           }
          //使用list记录strMapEntry便于根据下标获取
          List<Map.Entry<String,Set<Integer>>> strEntryList = new ArrayList<>();
          strEntryList.addAll(strMap.entrySet());
          int i = 0;
           for(Map.Entry<String,Set<Integer>> entry : patternMap.entrySet()){
           	 Set<Integer> pSet = entry.getValue();
             Map.Entry<String,Set<Integer>> strEntry =  strEntryList.get(i);
             Set<Integer> strSet = strEntry.getValue();
             if(pSet.size() != strSet.size()){
             	return false;
             }
             for(Integer n : pSet){
             	if(!strSet.contains(n)){
                	return false;
                } else{
                	strSet.remove(n);
                }
             }
             if(strSet.size() > 0){
             	return false;
             }
             i++;
           }
        }
    return true;
  }
}



4.两个线程交替打印1-100的整数
题目说明
两个线程交替打印1-100的整数，一个打印奇数，一个打印偶数，要求输出结果有序









*/