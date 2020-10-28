package com.ctt.web.service;

import org.opencv.core.Core;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @auther Administrator
 * @create 2020-03-06 下午 3:55
 */
@Service
public class BaseService {

      /**
     * 初始时间截 (2017-01-01)
     */
    private static final long INITIAL_TIME_STAMP = 1483200000000L;

    /**
     * 机器id所占的位数
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * 数据标识id所占的位数
     */
    private static final long DATACENTER_ID_BITS = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);

    /**
     * 序列在id中占的位数
     */
    private final static long SEQUENCE_BITS = 12L;

    /**
     * 机器ID的偏移量(12)
     */
    private final static long WORKERID_OFFSET = SEQUENCE_BITS;

    /**
     * 数据中心ID的偏移量(12+5)
     */
    private final static long DATACENTERID_OFFSET = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间截的偏移量(5+5+12)
     */
    private final static long TIMESTAMP_OFFSET = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     **/
    private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * 工作节点ID(0~31)
     **/
    private static long workerId = 1;
    /**
     * 数据中心ID(0~31)
     **/
    private static long datacenterId = 1;

    /**
     * 毫秒内序列(0~4095)
     */
    private static long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private static long lastTimestamp = -1L;

    /**
     * 获得下一个ID (用同步锁保证线程安全) twwiter snowflake 算法
     *
     * @return SnowflakeId
     * @throws InterruptedException
     */
    protected synchronized String nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {//避免冲突
            throw new RuntimeException("当前时间小于上一次记录的时间戳！");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - INITIAL_TIME_STAMP) << TIMESTAMP_OFFSET) | (datacenterId << DATACENTERID_OFFSET)
                | (workerId << WORKERID_OFFSET) | sequence;
        return Long.toString(id);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
