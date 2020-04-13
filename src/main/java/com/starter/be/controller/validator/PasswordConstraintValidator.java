package com.starter.be.controller.validator;

import com.starter.be.exception.ServiceException;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

  private static final int PASSWORD_MIN_LENGTH = 8;
  private static final int PASSWORD_MAX_LENGTH = 64;

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    PasswordValidator validator = createValidator();
    RuleResult result = validator.validate(new PasswordData(password));
    if (!result.isValid()) {
      throw new ServiceException("Password does not meet complexity requirements");
    }
    return true;
  }

  private PasswordValidator createValidator() {
    return new PasswordValidator(
        Arrays.asList(
            new LengthRule(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Alphabetical, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new WhitespaceRule()));
  }
}
