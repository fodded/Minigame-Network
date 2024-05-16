package me.fodded.common.data.transfer;

public interface IRedisListener {
    void onMessage(CharSequence channel, Object msg);
}
