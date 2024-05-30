package dev.bpeeva.bookllegro.util.credentials;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
public class CredentialManagerTests {

    @Test
    void shouldSuccessfullyValidateAnEmail() {
        //Arrange
        String emailToValidate = "blueMonkey@gmail.com";
        String emailToValidate2 = "yellow_fl4sh@onet.pl";

        //Apply
        boolean isEmail = CredentialManager.isEmail(emailToValidate);
        boolean isEmail2 = CredentialManager.isEmail(emailToValidate2);

        //Assert
        assertThat(isEmail).isTrue();
        assertThat(isEmail2).isTrue();
    }

    @Test
    void shouldFailTheEmailValidation() {
        //Arrange
        String emailToValidate = "123xada@@onet.pl";
        String emailToValidate2 = "@adam.fb.pl";

        //Apply
        boolean isEmail = CredentialManager.isEmail(emailToValidate);
        boolean isEmail2 = CredentialManager.isEmail(emailToValidate2);

        //Assert
        assertThat(isEmail).isFalse();
        assertThat(isEmail2).isFalse();
    }

    @Test
    void shouldSuccessfullyValidateAPassword() {
        //Arrange
        String passwordToValidate = "oijo234y2340328fla";
        String passwordToValidate2 = "al;jf;dsj;kja34w43";

        //Apply
        boolean isValid = CredentialManager.isPasswordValid(passwordToValidate);
        boolean isValid2 = CredentialManager.isPasswordValid(passwordToValidate2);

        //Assert
        assertThat(isValid).isTrue();
        assertThat(isValid2).isTrue();
    }

    @Test
    void shouldFailToValidateAPassword() {
        //Arrange
        String passwordToValidate = "xDDD";
        String passwordToValidate2 = "23111";

        //Apply
        boolean isValid = CredentialManager.isPasswordValid(passwordToValidate);
        boolean isValid2 = CredentialManager.isPasswordValid(passwordToValidate2);

        //Assert
        assertThat(isValid).isFalse();
        assertThat(isValid2).isFalse();
    }

}
