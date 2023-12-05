package org.javaSemantic;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.javaSemantic.visitors.NullCheckVisitor;
import org.javaSemantic.visitors.ReturnTypeCheckingVisitor;
import org.javaSemantic.visitors.TypeAndDivisionCheckingVisitor;
import org.javaSemantic.visitors.TypeCheckingVisitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class JavaCodeAnalyzer {
    private  static CompilationUnit cu;
    public  JavaCodeAnalyzer(String filePath) throws IOException {
        ParserConfiguration parserConfiguration = new ParserConfiguration().setSymbolResolver(
                new JavaSymbolSolver(new ReflectionTypeSolver()));

        FileInputStream fileInputStream = new FileInputStream(filePath);
        StaticJavaParser.setConfiguration(parserConfiguration);
        cu = StaticJavaParser.parse(fileInputStream);

        new TypeCheckingVisitor().visit(cu, null);
        new TypeAndDivisionCheckingVisitor().visit(cu, null);
        new ReturnTypeCheckingVisitor().visit(cu, null);
        new NullCheckVisitor().visit(cu, null);
    }


}
