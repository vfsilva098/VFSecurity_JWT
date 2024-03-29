package br.com.vfsilva.jwt.auth.util.exception;

import br.com.vfsilvacore.util.message.ErrorMessage;

public class VfsilvaUnauthorizedException extends BasicException {
    public VfsilvaUnauthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public static void invoke(ErrorMessage errorMessage) {
        throw new VfsilvaUnauthorizedException(errorMessage);
    }
}
