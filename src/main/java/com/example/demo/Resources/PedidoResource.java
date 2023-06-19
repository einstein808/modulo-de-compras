package com.example.demo.Resources;

import com.example.demo.Model.Pedido;
import com.example.demo.Services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

    private PedidoService pedidoService;

    public PedidoResource(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntity<String>> salvarPedido(@RequestBody Pedido pedido) {
        ResponseEntity<String> pedidoSalvo = pedidoService.salvarPedido(pedido);
        return ResponseEntity.ok(pedidoSalvo);
    }
    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }
}
