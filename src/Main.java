import com.r6mulo.currency.api.ApiClient;
import com.r6mulo.currency.model.ConversionHistory;
import com.r6mulo.currency.model.ConversionResult;
import com.r6mulo.currency.service.CurrencyService;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ApiClient api = new ApiClient();
        CurrencyService service = new CurrencyService(api);

        // Histórico de conversões
        ConversionHistory history = new ConversionHistory();

        // Carregar symbols antes de começar
        try {
            System.out.println("Carregando lista de moedas...");
            service.loadSymbols();
            System.out.println("Moedas carregadas: " + service.getSymbols().size());
        } catch (Exception e) {
            System.err.println("Falha ao carregar moedas: " + e.getMessage());
            // podemos continuar, mas validação de códigos não funcionará
        }

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(6);

        while (running) {

            // Exibir histórico antes do menu
            if (!history.isEmpty()) {
                history.print();
            }

            printMenu();
            String opt = sc.nextLine().trim();
            try {
                switch (opt) {
                    case "1": doFixed(service, sc, "BRL", "USD", nf, history); break;
                    case "2": doFixed(service, sc, "USD", "BRL", nf, history); break;
                    case "3": doFixed(service, sc, "EUR", "USD", nf, history); break;
                    case "4": doFixed(service, sc, "USD", "EUR", nf, history); break;
                    case "5": doFixed(service, sc, "BRL", "EUR", nf, history); break;
                    case "6": doFixed(service, sc, "EUR", "BRL", nf, history); break;
                    case "7": doCustom(service, sc, nf, history); break;
                    case "8": listSymbols(service); break;
                    case "9": history.clear(); break;
                    case "0": running=false; System.out.println("Saindo..."); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("=== Conversor de Moedas ===");
        System.out.println("1) BRL -> USD");
        System.out.println("2) USD -> BRL");
        System.out.println("3) EUR -> USD");
        System.out.println("4) USD -> EUR");
        System.out.println("5) BRL -> EUR");
        System.out.println("6) EUR -> BRL");
        System.out.println("7) Conversão personalizada");
        System.out.println("8) Listar moedas suportadas (código - descrição)");
        System.out.println("9) Limpar histórico");
        System.out.println("0) Sair");
        System.out.print("Escolha: ");
    }

    private static void doFixed(CurrencyService service,
                                Scanner sc,
                                String from,
                                String to,
                                NumberFormat nf,
                                ConversionHistory history) throws Exception {
        System.out.print("Valor em " + from + ": ");
        double amount = readAmount(sc);
        ConversionResult res = service.convert(from, to, amount);
        printConversion(amount, from, res, to, nf, history);
    }

    private static void doCustom(CurrencyService service,
                                 Scanner sc,
                                 NumberFormat nf,
                                 ConversionHistory history) throws Exception {
        System.out.print("Moeda origem (ex: USD): ");
        String from = sc.nextLine().trim().toUpperCase();
        System.out.print("Moeda destino (ex: BRL): ");
        String to = sc.nextLine().trim().toUpperCase();

        if (!service.isValidCode(from) || !service.isValidCode(to)) {
            System.out.println("Uma ou ambas moedas não são válidas. Use a opção 8 para listar códigos suportados.");
            return;
        }

        System.out.print("Valor em " + from + ": ");
        double amount = readAmount(sc);
        ConversionResult res = service.convert(from, to, amount);
        printConversion(amount, from, res, to, nf, history);
    }

    private static double readAmount(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim().replace(",", ".");
            try {
                double v = Double.parseDouble(s);
                if (v < 0) {
                    System.out.print("Informe valor positivo: ");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Digite novamente: ");
            }
        }
    }

    private static void printConversion(double amount,
                                        String from,
                                        ConversionResult res,
                                        String to,
                                        NumberFormat nf,
                                        ConversionHistory history) {
        System.out.printf("%s %s = %s %s%n",
                nf.format(amount), from,
                nf.format(res.getResult()), to);

        if (!Double.isNaN(res.getRate())) {
            System.out.printf("Taxa: 1 %s = %.6f %s%n", from, res.getRate(), to);
        }
        if (res.getDate() != null && !res.getDate().isEmpty()) {
            System.out.println("Data: " + res.getDate());
        }

        // Registra a conversão no histórico
        history.add(String.format("%s %s = %s %s (taxa %.6f) - %s",
                nf.format(amount), from,
                nf.format(res.getResult()), to,
                res.getRate(),
                res.getDate()));
    }

    private static void listSymbols(CurrencyService service) {
        Map<String,String> syms = service.getSymbols();
        if (syms.isEmpty()) {
            System.out.println("Nenhuma moeda carregada (tente recarregar a aplicação).");
            return;
        }
        syms.forEach((k,v) -> System.out.println(k + " - " + v));
    }
}