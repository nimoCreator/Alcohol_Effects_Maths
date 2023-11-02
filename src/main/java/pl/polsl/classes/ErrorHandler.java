package pl.polsl.classes;

import java.util.ArrayList;

/**
 * @author nimo
 */
public class ErrorHandler {
    public static class ErrorUnit {
        private final Integer errorCode;
        private final String errorDetails;

        public ErrorUnit(Integer errorCode, String errorDetails) 
        {
            this.errorCode = errorCode;
            this.errorDetails = errorDetails;
        }

        public Integer getErrorCode() 
        {
            return errorCode;
        }

        public String getErrorDetails() 
        {
            return errorDetails;
        }

        @Override
        public String toString() {
            return "["+(this.errorCode.equals(1) ? 'i' : '!')+"] " + errorCode + " \t: " + errorDetails;
        }
    }

    private final ArrayList<ErrorUnit> errors;

    public ErrorHandler() { this.errors = new ArrayList<>(); }

    public void addError(ErrorUnit error) { errors.add(error); }
    
    public void addError(Integer errorCode , String errorDetails) { errors.add(new ErrorUnit(errorCode, errorDetails));  }

    public Integer count() { return errors.size(); }
    
    public String popAllAsString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (ErrorUnit error : errors)
        {
            stringBuilder.append(error.toString()).append("\n");
        }

        errors.clear();
        return stringBuilder.toString();
    }
}

/*

    0 - no error data
    1 - debug info
    >2 - errors:
        
        


*/