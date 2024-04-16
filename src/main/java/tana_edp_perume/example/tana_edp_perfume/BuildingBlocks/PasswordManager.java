package tana_edp_perume.example.tana_edp_perfume.BuildingBlocks;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.ScryptFunction;
import com.password4j.types.Bcrypt;

public class PasswordManager {

    public  String HashedPassword (String password){
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);
        Hash hash = Password.hash(password)
                .addPepper("shared-secret")
                .with(bcrypt);
       return   hash.getResult();
    }
    public Boolean CheckPassword(String password,String passwordHashed){
        BcryptFunction bcrypt = BcryptFunction.getInstanceFromHash(passwordHashed);
        boolean verified = Password.check(password, passwordHashed)
                .addPepper("shared-secret")
                .with(bcrypt);
        return  verified;
    }
}
