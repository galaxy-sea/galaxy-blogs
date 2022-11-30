package plus.wcj.abac;

import org.junit.jupiter.api.Test;
import plus.wcj.abac.entity.User;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/11/30
 */
@SpringBootTest
public class ExpressionParserTest {

    public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();


    /**
     * 测试ExpressionParser会不会调用到Spring bean
     */
    @Test
    public void testBean() throws Exception {
        Expression expression = EXPRESSION_PARSER.parseExpression("@UserService.get(1)");
        try {
            User value = expression.getValue(User.class);
            System.out.println(value);
            throw new Exception("调用到了 Spring bean, 会存在安全问题哦");
        } catch (EvaluationException e) {
        }

    }


}
