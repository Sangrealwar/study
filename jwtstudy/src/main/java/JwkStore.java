import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/7/8.
 */
public class JwkStore {

    public RSAPublicKey get(String key) {
        if (key.equals("publicKey")) {
            KeyPairGenerator keyPairGenerator = null;
            try {
                keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            //获取公钥，并以base64格式打印出来
            PublicKey publicKey = keyPair.getPublic();
            return (RSAPublicKey) publicKey;
        }
        return null;
    }
}
