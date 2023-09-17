package Data.Validators.ColumnValidators;

import Data.Validators.ConstraintType;
import Data.Validators.Validator;
import Data.Validators.ValidatorFactory;

public class ColumnValidatorFactory implements ValidatorFactory {
    @Override
    public Validator createValidator(ConstraintType constraintType) {
        switch (constraintType) {
            case NOT_NULL -> {
                return new NotNullValidator();
            }
            case CHECK -> {
                throw new UnsupportedOperationException();
                //new ColumnCheckValidator(); // TODO: Implement the how
            }
            default -> throw new IllegalArgumentException("Invalid constraint type: " + constraintType);
        }
    }
}
