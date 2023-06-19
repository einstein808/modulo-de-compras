package com.example.demo.Services;

import com.example.demo.Model.Item;
import com.example.demo.Model.Pedido;
import com.example.demo.Model.Produto;
import com.example.demo.Repository.PedidoRepository;
import com.example.demo.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;


//    public Pedido salvarPedido(Pedido pedido) {
//        // Verificar cada item do pedido
//        for (Item item : pedido.getItems()) {
//            Produto produto = item.getProduto();
//
//            // Verificar se o produto já existe
//            if (produto.getId() == null) {
//                // O produto não existe, então salve-o antes de atribuí-lo ao item
//                produtoRepository.save(produto);
//            }
//
//            // Agora você pode atribuir o produto ao item
//            item.setProduto(produto);
//        }
//
//        // Agora você pode salvar o pedido
//        pedidoRepository.save(pedido);
//        return pedido;
//    }
public Pedido salvarPedido(Pedido pedido) {
    // Verificar cada item do pedido
    for (Item item : pedido.getItems()) {
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
    double valorTotal = 0.0;
    for (Item item : pedido.getItems()) {
        double valorItem = item.getProduto().getPreco();
        int quantidadeItem = item.getQuantidade();
        double valorItemTotal = valorItem * quantidadeItem;

        valorTotal += valorItemTotal;
    }

    // Atribuir o valor total ao pedido
    pedido.setValorTotal(valorTotal);

    // Agora você pode salvar o pedido
    pedidoRepository.save(pedido);
    return pedido;
}




}
