package me.fodded.proxyloadbalancer.info.network.listeners.redis;

import me.fodded.common.data.statistics.transfer.IRedisListener;

/**
 * The packets which are sent from proxy side to proxy load balancer are being caught with redis here
 */
public class RedisPlayerChangeServer implements IRedisListener {

    @Override
    public void onMessage(CharSequence channel, Object msg) {

    }
}
