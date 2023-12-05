package org.javaSemantic.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import javassist.compiler.ast.Variable;

import java.sql.SQLSyntaxErrorException;
import java.util.*;

public class NullCheckVisitor extends VoidVisitorAdapter<Void> {
    private Map<String, Expression> variables = new HashMap<>();

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        super.visit(n, arg);
        getVariables(n);

        variables.forEach((name, value) -> {
            if(value == null){
                System.out.printf("Warning: variable \"%s\" may be null\n", name);
            }
        });
        variables.clear();


    }

    private void getVariables(MethodDeclaration n){
        n.getBody().ifPresent(body -> {
            body.getStatements().stream()
                    .filter(Statement::isExpressionStmt)
                    .map(ExpressionStmt.class::cast)
                    .map(ExpressionStmt::getExpression)
                    .filter(expression -> expression instanceof VariableDeclarationExpr)
                    .map(VariableDeclarationExpr.class::cast)
                    .forEach(v -> { v.getVariables().forEach(variable -> {
                        if(variable.getInitializer().isPresent())
                            variables.put(variable.getNameAsString(), variable.getInitializer().get());
                        else
                            variables.put(variable.getNameAsString(), null);
                        }
                    );
                    });
        });

        n.getBody().ifPresent(body -> {
            body.getStatements().stream()
                    .filter(Statement::isExpressionStmt)
                    .map(ExpressionStmt.class::cast)
                    .map(ExpressionStmt::getExpression)
                    .filter(expression -> expression instanceof AssignExpr)
                    .map(AssignExpr.class::cast)
                    .forEach(assignExpr -> {
                        String variableName = assignExpr.getTarget().asNameExpr().getNameAsString();
                        variables.replace(variableName, assignExpr.getValue());
                    });
        });
    }
}
