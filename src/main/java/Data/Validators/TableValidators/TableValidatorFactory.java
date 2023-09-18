package Data.Validators.TableValidators;

import Data.Validators.ConstraintType;
import Data.Validators.Validator;
import Data.Validators.ValidatorFactory;

public class TableValidatorFactory implements ValidatorFactory {

    @Override
    public Validator createValidator(ConstraintType constraintType) {
        return switch (constraintType) {
            case PRIMARY_KEY -> new PrimaryKeyValidator();
            case FOREIGN_KEY ->
                    throw new UnsupportedOperationException();// ForeignKeyValidator(); // TODO: Figure out how to implement these
            case UNIQUE -> new UniqueValidator();
            case CHECK ->
                    throw new UnsupportedOperationException();//new CheckValidator();  // TODO: Figure out how to implement these
            default -> throw new IllegalArgumentException("Invalid constraint type: " + constraintType);
        };
    }
}
