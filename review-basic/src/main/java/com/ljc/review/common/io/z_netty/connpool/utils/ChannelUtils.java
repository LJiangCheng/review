package com.ljc.review.common.io.z_netty.connpool.utils;

import io.netty.util.AttributeKey;

import java.util.Map;

public class ChannelUtils {
    public static final AttributeKey<Map<Integer, Object>> DATA_MAP_ATTRIBUTEKEY = AttributeKey.valueOf("dataMap");

}
