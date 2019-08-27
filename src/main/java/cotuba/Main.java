package cotuba;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LeitorOpcoesCLI opcoesCLI = new LeitorOpcoesCLI(args);
        Path diretorioDosMD = opcoesCLI.getDiretorioDosMD();
        String formato = opcoesCLI.getFormato();
        Path arquivoDeSaida = opcoesCLI.getArquivoDeSaida();
        boolean modoVerboso = opcoesCLI.isModoVerboso();

        RenderizadorMDParaHTML renderizador = new RenderizadorMDParaHTML();
        List<Capitulo> capitulos = renderizador.renderiza(diretorioDosMD);

        Ebook ebook = new Ebook();
        ebook.setFormato(formato);
        ebook.setArquivoDeSaida(arquivoDeSaida);
        ebook.setCapitulos(capitulos);

        try {
            if ("pdf".equals(formato)) {
                GeradorPDF geradorPDF = new GeradorPDF();
                geradorPDF.gera(ebook);

            } else if ("epub".equals(formato)) {
                GeradorEPUB geradorEPUB = new GeradorEPUB();
                geradorEPUB.gera(ebook);

            } else {
                throw new RuntimeException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }

}
