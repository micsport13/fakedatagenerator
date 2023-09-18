package Data.Validators.TableValidators;

/**
 * The type Table check constraint.
 */
public class TableCheckValidator implements TableValidator {
    // TODO: Figure out how to link other columns to this constraint

    @Override
    public boolean validate(Object value) {
        throw new UnsupportedOperationException("Check constraint validation not yet implemented");
    }

    @Override
    public String toString() {
        return "Check"; // TODO: Update with attributes once defined
    }
}
