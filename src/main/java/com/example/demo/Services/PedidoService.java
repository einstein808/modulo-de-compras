package com.example.demo.Services;

import com.example.demo.Model.Item;
import com.example.demo.Model.Pedido;
import com.example.demo.Model.Produto;
import com.example.demo.Repository.PedidoRepository;
import com.example.demo.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;



import org.springframework.http.HttpStatus;


import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;


    @Value("http://localhost:8080/api/pedido/aprovar")
    private String outraApiUrl;

    public ResponseEntity<String> salvarPedido(Pedido pedido) {
        RestTemplate restTemplate = new RestTemplate();
        double valorTotal = 0.0;
        String status = "";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Pedido> requestEntity = new HttpEntity<>(pedido, headers);
        ResponseEntity<Pedido> response = restTemplate.exchange(outraApiUrl, HttpMethod.POST, requestEntity, Pedido.class);

        // Verificar cada item do pedido
        if (response.getStatusCode().is2xxSuccessful()) {
            Pedido pedidoAprovado = response.getBody();
            if (response.getBody().isAprovado()){
                pedidoAprovado.setAprovado(true);
                for (Item item : pedidoAprovado.getItems()) {
                    Produto produto = item.getProduto();

                    // Verificar se o produto já existe
                    if (produto.getId() == null) {
                        // O produto não existe, então salve-o antes de atribuí-lo ao item
                        produtoRepository.save(produto);
                    }

                    // Agora você pode atribuir o produto ao item
                    item.setProduto(produto);
                }

                // Calcular o valor total do pedido
                for (Item item : pedidoAprovado.getItems()) {
                    double valorItem = item.getProduto().getPreco();
                    int quantidadeItem = item.getQuantidade();
                    double valorItemTotal = valorItem * quantidadeItem;
                    valorTotal += valorItemTotal;
                }

                // Atribuir o valor total ao pedido
                pedidoAprovado.setValorTotal(valorTotal);

                // Agora você pode salvar o pedido
                pedidoRepository.save(pedidoAprovado);
                return ResponseEntity.ok("Aprovado");


            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pagamento Negado");
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro ao processar");
    }

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }
}






