package com.example.demo.Resources;
import com.example.demo.Model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedido")
public class PedidoResourcet {

    @PostMapping("/aprovar")
    public ResponseEntity<Pedido> aprovarPedido(@RequestBody Pedido pedido) {
        // Modificar a propriedade "aprovado" do pedido
        pedido.setAprovado(true);

        // Realizar outras operações necessárias

        // Retornar o pedido atualizado
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }
}
