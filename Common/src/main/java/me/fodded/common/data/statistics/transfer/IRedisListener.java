package me.fodded.common.data.statistics.transfer;

public interface IRedisListener {
    void onMessage(CharSequence channel, Object msg);
}
