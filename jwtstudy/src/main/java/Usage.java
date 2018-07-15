import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/7/8.
 */
public class Usage {


    public static void main(String[] args) throws Exception {
        verify();
    }

    private static void verify() throws Exception {
        String token = createHS256ignToken();
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    //验证声明
                    .withIssuer("my_auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            //获得headerClaim
            System.out.println("====================header_Claim==================");
            //自定义的类型
            Claim claim = jwt.getHeaderClaim("owner");
            if (!claim.isNull()) {
                System.out.println("headerClaim:" + claim.asString());
            }
            //获得Payload
            System.out.println("====================payload_Claim==================");
            //预定义的类型
            System.out.println("Issuer:" + jwt.getIssuer());

            System.out.println("====================签名结构==================");
            System.out.println("header:" + jwt.getHeader());
            System.out.println("payload:" + jwt.getPayload());
            System.out.println("signature:" + jwt.getSignature());
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            //验证通过就不会报错
        }
        //RSA同理，只是Algorithm换成RSA的静态方法
    }

    private static String createHS256ignToken() throws Exception {
        try {
            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("owner", "auth0");

            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withHeader(headerClaims)
                    //自定义声明（claims）
                    .withIssuer("my_auth0")
                    .sign(algorithm);
            System.out.println(token);
            return token;
        } catch (JWTCreationException exception) {
            //声明必须可以转化成json
        }
        return "";
    }

    private static String createRSASignToken() throws Exception {
        RSAPublicKey publicKey = (RSAPublicKey) getPublicKey();
        RSAPrivateKey privateKey = (RSAPrivateKey) getPrivateKey();
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
            System.out.println(token);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return "";
    }

    private static void useKeyProvider() throws Exception {
        final JwkStore jwkStore = new JwkStore();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        final PrivateKey privateKey = keyPair.getPrivate();

        RSAKeyProvider keyProvider = new RSAKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String kid) {
                //Received 'kid' value might be null if it wasn't defined in the Token's header
                RSAPublicKey publicKey = jwkStore.get(kid);
                return publicKey;
            }

            @Override
            public RSAPrivateKey getPrivateKey() {
                return (RSAPrivateKey) privateKey;
            }

            @Override
            public String getPrivateKeyId() {
                return null;
            }
        };
        Algorithm algorithm = Algorithm.RSA256(keyProvider);
    }

    private static void useStaticSecretOrKeys() throws Exception {
        //HMAC
        Algorithm algorithmHS = Algorithm.HMAC256("secret");

        //RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥：" + new String(Base64.getEncoder().encode(publicKey.getEncoded())));

        //获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("私钥：" + new String(Base64.getEncoder().encode(privateKey.getEncoded())));

        //RSA
        Algorithm algorithmRS = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
    }

    private static PublicKey getPublicKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        return publicKey;
    }

    private static PrivateKey getPrivateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        return privateKey;
    }
}
