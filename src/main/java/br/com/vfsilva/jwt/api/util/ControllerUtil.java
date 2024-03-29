package br.com.vfsilva.jwt.api.util;

import br.com.vfsilva.jwt.auth.util.exception.VfsilvaException;
import br.com.vfsilvacore.util.message.ErrorMessage;
import br.com.vfsilvacore.util.message.stack.ErrorStack;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Classe utilitária controller
 *
 * @param <APP> Classe de aplicação
 * @param <P>   Classe de parâmetro para realizar o login
 */
public abstract class ControllerUtil<APP, P> {

    @Autowired
    public ErrorStack errorStack;

    /**
     * Método responsável por acessar o método da classe APP com o nome de "login"
     *
     * @param app   Instância da classe de aplicação
     * @param param Parâmetros do método "login" da classe APP
     * @return Objeto de retorno do método "login" da classe APP
     */
    Object executeMethodLogin(APP app, P param) {
        try {
            return app.getClass().getMethod("login", param.getClass()).invoke(app, param);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new VfsilvaException(generateError(null, errorStack.getErrors()));
        } catch (NoSuchMethodException ex) {
            errorStack.addMessage("Método login não encontrado.");
            throw new VfsilvaException(generateError(null, errorStack.getErrors()));
        }
    }

    /**
     * Método responsável por acessar o método da classe APP com o nome de "refreshToken"
     *
     * @param app Instância da classe de aplicação
     * @return Objeto de retorno do método "refreshToken" da classe APP
     */
    Object executeMethodRefreshToken(APP app) {
        try {
            return app.getClass().getMethod("refreshToken").invoke(app);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new VfsilvaException(generateError(null, errorStack.getErrors()));
        } catch (NoSuchMethodException ex) {
            errorStack.addMessage("Método refreshToken não encontrado.");
            throw new VfsilvaException(generateError(null, errorStack.getErrors()));
        }
    }

    /**
     * Método para gerar ErrorMessage
     *
     * @param error   Erro lançado.
     * @param details Lista com errors atribuidos nas classes que utilizam ErrorStack
     * @return ErrorMessage
     */
    private ErrorMessage generateError(final String error, final List<ErrorMessage> details) {
        return ErrorMessage.builder()
                .title("Atenção")
                .error(error)
                .details(details)
                .build();
    }
}
