import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Programa9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> contra = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        System.out.println("Validador de contraseñas.");
        System.out.println("Ingrese las contraseñas que desea validar.");
        System.out.println("Escriba 'salir' para terminar:");

        while (true) {
            System.out.print("Ingrese una contraseña: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("salir")) {
                break;
            }
            contra.add(input);
        }
        for (String contrasena : contra) {
            executor.execute(new ValidadorContrasena(contrasena));
        }
        executor.shutdown();

        System.out.println("Validación en proceso.");
        scanner.close();
    }
}
class ValidadorContrasena implements Runnable {
    private final String contrasena;

    public ValidadorContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public void run() {
        if (esContrasenaValida(contrasena)) {
            System.out.println("La contraseña '" + contrasena + "' es válida.");
        } else {
            System.out.println("La contraseña '" + contrasena + "' no cumple con los requisitos.");
        }
    }
    private boolean esContrasenaValida(String contrasena) {
        if (contrasena.length() < 8) return false;
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(contrasena).find()) return false;
        if (Pattern.compile("[A-Z]").matcher(contrasena).results().count() < 2) return false;
        if (Pattern.compile("[a-z]").matcher(contrasena).results().count() < 3) return false;
        if (!Pattern.compile("[0-9]").matcher(contrasena).find()) return false;
        return true;
    }
}