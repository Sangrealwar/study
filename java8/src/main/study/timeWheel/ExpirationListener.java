package study.timeWheel;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/6/6.
 */
public interface ExpirationListener<E> {

    /**
     * Invoking when a expired event occurs.
     *
     * @param expiredObject
     */
    void expired(E expiredObject);
}
