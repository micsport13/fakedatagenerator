package Data.Validators;

public interface ValidatorFactory {
    Validator createValidator(ConstraintType constraintType);
}
