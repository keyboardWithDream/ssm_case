import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author Harlan
 * @Date 2020/9/26
 */
public class test {

    @Test
    public void a(){
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
        System.out.println(bc.matches("123","$2a$10$a7g5jG30gBVsb5sfv2K/ie3mYHsk37rf3sn9fLT46WyUKyCuAIfha"));
        System.out.println(bc.encode("123"));
    }
}
