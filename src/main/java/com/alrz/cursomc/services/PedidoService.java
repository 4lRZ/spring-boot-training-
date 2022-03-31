package com.alrz.cursomc.services;

import com.alrz.cursomc.entities.ItemPedidoEntity;
import com.alrz.cursomc.entities.PagamentoComBoletoEntity;
import com.alrz.cursomc.entities.PedidoEntity;
import com.alrz.cursomc.entities.enums.EstadoPagamento;
import com.alrz.cursomc.repositories.ItemPedidoRepository;
import com.alrz.cursomc.repositories.PagamentoRepository;
import com.alrz.cursomc.repositories.PedidoRepository;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    final PedidoRepository PEDIDO_REPOSITORY;
    final BoletoService BOLETO_SERVICE;
    final PagamentoRepository PAGAMENTO_REPOSITORY;
    final ProdutoService PRODUTO_SERVICE;
    final ItemPedidoRepository ITEMPEDIDO_REPOSITORY;

    public PedidoService(PedidoRepository repository, BoletoService boleto_service, PagamentoRepository pagamento_repository, ProdutoService produto_service, ItemPedidoRepository itempedido_repository) {
        PEDIDO_REPOSITORY = repository;
        BOLETO_SERVICE = boleto_service;
        PAGAMENTO_REPOSITORY = pagamento_repository;
        PRODUTO_SERVICE = produto_service;
        ITEMPEDIDO_REPOSITORY = itempedido_repository;
    }

    public PedidoEntity find(Long id) {
        Optional<PedidoEntity> find = PEDIDO_REPOSITORY.findById(id);
        return find.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + PedidoEntity.class.getName()));
    }

    public PedidoEntity insert(PedidoEntity obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoletoEntity) {
            PagamentoComBoletoEntity pagto = (PagamentoComBoletoEntity) obj.getPagamento();
            BOLETO_SERVICE.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = PEDIDO_REPOSITORY.save(obj);
        PAGAMENTO_REPOSITORY.save(obj.getPagamento());
        for (ItemPedidoEntity ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(PRODUTO_SERVICE.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        ITEMPEDIDO_REPOSITORY.saveAll(obj.getItens());
        return obj;
    }
}
