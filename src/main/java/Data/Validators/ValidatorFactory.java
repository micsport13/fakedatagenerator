package Data.Validators;

public interface ValidatorFactory {
    public Validator createValidator(ConstraintType constraintType);
}
