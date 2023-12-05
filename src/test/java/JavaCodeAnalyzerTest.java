import org.javaSemantic.JavaCodeAnalyzer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;


public class JavaCodeAnalyzerTest {
    private final ByteArrayOutputStream outError = new ByteArrayOutputStream();

    @Before
    public  void setOutError(){
        System.setOut(new PrintStream(outError));
    }
    @After
    public  void restoreStream(){
        System.setOut(System.out);
    }
    private String path = "src/test/resources/";

    @Test
    @DisplayName("Correct init Test")
    public void CorrectInitializeTypeTest() {
        try {
            new JavaCodeAnalyzer(path + "CorrectInitializeTypeTest.java");
        }catch (IOException ex){
            ex.printStackTrace();
        }
        outError.reset();
    }

    @Test
    @DisplayName("Incorrect init Test")
    public void IncorrectInitializeTypeTest() {
        try {
            new JavaCodeAnalyzer(path + "IncorrectInitializeTypeTest.java");

        }catch (IOException ex){
            ex.printStackTrace();
        }
        assertEquals("Error(line 7,col 9) Type mismatch in variable 'stringValue'. Expected type: String, but found type: PrimitiveTypeUsage{name='int'}\n" +
                "Error(line 9,col 9) Type mismatch in variable 's'. Expected type: StringBuilder, but found type: ReferenceType{java.lang.String, typeParametersMap=TypeParametersMap{nameToValue={}}}\n",
                outError.toString());

        outError.reset();

    }

    @Test
    @DisplayName("Correct Division Test")
    public void CorrectDivisionTest() {
        try {
            new JavaCodeAnalyzer (path + "CorrectDivisionTest.java");

        }catch (IOException ex){
            ex.printStackTrace();
        }
        assertEquals("", outError.toString());
        outError.reset();
    }

    @Test
    @DisplayName("Incorrect Division Test")
    public void IncorrectDivisionTest() {
        try {
            new JavaCodeAnalyzer (path + "IncorrectDivisionTest.java");

        }catch (IOException ex){
            ex.printStackTrace();
        }
        assertEquals("Error(line 6,col 17) Potential division by zero in expression '12 / 0'\n" +
                    "Error(line 8,col 18) Non-numeric types used in arithmetic expression '10 / b'\n",
                    outError.toString());
        outError.reset();
    }

    @Test
    @DisplayName("Incorrect return type Test")
    public void IncorrectReturnTypeTypeTest() {
        try {
            new JavaCodeAnalyzer (path + "IncorrectReturnTypeTest.java");

        }catch (IOException ex){
            ex.printStackTrace();
        }
        assertEquals("Error:(line 4,col 12) Return type mismatch in method 'foo1'. Declared return type: int, but found type: java.lang.String\n" +
                     "Error:(line 8,col 12) Return type mismatch in method 'foo2'. Declared return type: String, but found type: int\n",
                     outError.toString());
        outError.reset();
    }
    @Test
    @DisplayName("Correct return type Test")
    public void CorrectReturnTypeTypeTest() {
        try {
            new JavaCodeAnalyzer (path + "CorrectReturnTypeTest.java");

        }catch (Exception ex){
            ex.printStackTrace();
        }
        assertEquals("", outError.toString());
        outError.reset();
    }

    @Test
    @DisplayName("Incorrect check for null variables test ")
    public void IncorrectNullCheck() {
        try {
            new JavaCodeAnalyzer (path + "NullCheckTest.java");

        }catch (IOException ex){
            ex.printStackTrace();
        }
        assertEquals("Warning: variable \"b\" may be null\n" +
                     "Warning: variable \"str\" may be null\n",
                     outError.toString());
        outError.reset();
    }
}
