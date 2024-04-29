package clinica.med.Projeto_Clinica.domain.medico;
import clinica.med.Projeto_Clinica.domain.endereco.DTOEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//cLASSE PARA TRANSFERENCIA DE DADOS
public record DTOMedico( //NOTBLANK VERIFICA SE ESTA ESPACO VAZIO E NULO
                        @NotBlank
                          String nome,
                          @NotBlank
                         // ESTA ANOTACAO FAZ O CORPO TER OBRIGACAO DE TER @ E ETC
                         @Email
                          String email,

                         @NotBlank
                         String telefone,

                          @NotBlank
                          //MOSTRA QUE TEM QUE TER DE X A Y NUMEROS
                         @Pattern(regexp = "\\d{4,6}")
                          String crm,

                         // NOTNULL NAO PODE SER UM CAMPO NULO
                          @NotNull
                          Especialidade especialidade,

                          @NotNull
                         // VALID MOSTRA QUE ESSE ATRIBUTO TBM É DTO E PRECISA DE VALIDACAO
                         @Valid DTOEndereco endereco) {

    //OS PARAMETROS TEM ANOTACOES DE VALIDACAO.
}
