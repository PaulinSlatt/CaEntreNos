package clinica.med.CaEntreNos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-docs")
public class SwaggerController {

    @Operation(summary = "Documentação das operações da API Responsavel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/responsavel")
    public ResponseEntity<String> documentarResponsavel() {
        return ResponseEntity.ok("Este endpoint documenta as operações do controlador de Responsavel, incluindo possíveis respostas como 200, 400, 401, 403, 404, e 500.");
    }

    @Operation(summary = "Documentação das operações da API Relato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/relato")
    public ResponseEntity<String> documentarRelato() {
        return ResponseEntity.ok("Este endpoint documenta as operações do controlador de Relato, incluindo possíveis respostas como 200, 400, 401, 403, 404, e 500.");
    }

    @Operation(summary = "Documentação das operações da API Aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/aluno")
    public ResponseEntity<String> documentarAluno() {
        return ResponseEntity.ok("Este endpoint documenta as operações do controlador de Aluno, incluindo possíveis respostas como 200, 400, 401, 403, 404, e 500.");
    }

    @Operation(summary = "Documentação das operações da API Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso proibido"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/admin")
    public ResponseEntity<String> documentarAdmin() {
        return ResponseEntity.ok("Este endpoint documenta as operações do controlador de Admin, incluindo possíveis respostas como 200, 400, 401, 403, 404, e 500.");
    }
}