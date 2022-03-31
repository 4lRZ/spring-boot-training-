package com.alrz.cursomc.services;

import com.alrz.cursomc.entities.*;
import com.alrz.cursomc.entities.enums.EstadoPagamento;
import com.alrz.cursomc.entities.enums.TipoCliente;
import com.alrz.cursomc.repositories.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class DBService {

    final CategoriaRepository categoriaRepository;
    final ProdutoRepository produtoRepository;
    final EstadoRepository estadoRepository;
    final CidadeRepository cidadeRepository;
    final ClienteRepository clienteRepository;
    final EnderecoRepository enderecoRepository;
    final PedidoRepository pedidoRepository;
    final PagamentoRepository pagamentoRepository;
    final ItemPedidoRepository itemPedidoRepository;

    public DBService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository, EstadoRepository estadoRepository, CidadeRepository cidadeRepository, ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }


    public void instantiateTestDatabase() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ProdutoEntity p1 = new ProdutoEntity(null, "Computador", 2000.00);
        ProdutoEntity p2 = new ProdutoEntity(null, "Impressora", 800.00);
        ProdutoEntity p3 = new ProdutoEntity(null, "Mouse", 80.00);
        ProdutoEntity p4 = new ProdutoEntity(null, "Mesa de escritório", 300.00);
        ProdutoEntity p5 = new ProdutoEntity(null, "Toalha", 50.00);
        ProdutoEntity p6 = new ProdutoEntity(null, "Colcha", 200.00);
        ProdutoEntity p7 = new ProdutoEntity(null, "Tv true color", 1200.00);
        ProdutoEntity p8 = new ProdutoEntity(null, "Roçadeira", 800.00);
        ProdutoEntity p9 = new ProdutoEntity(null, "Abajur", 100.00);
        ProdutoEntity p10 = new ProdutoEntity(null, "Pendente", 180.00);
        ProdutoEntity p11 = new ProdutoEntity(null, "Shampoo", 90.00);

        CategoriaEntity cat1 = new CategoriaEntity(null, "Informática");
        CategoriaEntity cat2 = new CategoriaEntity(null, "Escritório");
        CategoriaEntity cat3 = new CategoriaEntity(null, "Cama mesa e banho");
        CategoriaEntity cat4 = new CategoriaEntity(null, "Eletrônicos");
        CategoriaEntity cat5 = new CategoriaEntity(null, "Jardinagem");
        CategoriaEntity cat6 = new CategoriaEntity(null, "Decoração");
        CategoriaEntity cat7 = new CategoriaEntity(null, "Perfumaria");

        EstadoEntity est1 = new EstadoEntity(null, "Minas Gerais");
        EstadoEntity est2 = new EstadoEntity(null, "São Paulo");

        CidadeEntity c1 = new CidadeEntity(null, "Uberlândia", est1);
        CidadeEntity c2 = new CidadeEntity(null, "São Paulo", est2);
        CidadeEntity c3 = new CidadeEntity(null, "Campinas", est2);

        ClienteEntity cli1 = new ClienteEntity(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);

        EnderecoEntity e1 = new EnderecoEntity(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        EnderecoEntity e2 = new EnderecoEntity(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

        PedidoEntity ped1 = new PedidoEntity(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        PedidoEntity ped2 = new PedidoEntity(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

        PagamentoEntity pagto1 = new PagamentoComCartaoEntity(null, EstadoPagamento.QUITADO, ped1, 6);
        PagamentoEntity pagto2 = new PagamentoComBoletoEntity(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);

        ItemPedidoEntity ip1 = new ItemPedidoEntity(ped1, p1, 0.00, 1, 2000.00);
        ItemPedidoEntity ip2 = new ItemPedidoEntity(ped1, p3, 0.00, 2, 80.00);
        ItemPedidoEntity ip3 = new ItemPedidoEntity(ped2, p2, 100.00, 1, 800.00);

        ped1.setPagamento(pagto1);
        ped2.setPagamento(pagto2);
        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(List.of(ip3));

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(List.of(p2));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(List.of(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(List.of(p11));

        p1.getCategorias().addAll(List.of(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(List.of(cat2));
        p5.getCategorias().addAll(List.of(cat3));
        p6.getCategorias().addAll(List.of(cat3));
        p7.getCategorias().addAll(List.of(cat4));
        p8.getCategorias().addAll(List.of(cat5));
        p9.getCategorias().addAll(List.of(cat6));
        p10.getCategorias().addAll(List.of(cat6));
        p11.getCategorias().addAll(List.of(cat7));


        p1.getItens().addAll(List.of(ip1));
        p2.getItens().addAll(List.of(ip3));
        p3.getItens().addAll(List.of(ip2));

        est1.getCidades().addAll(List.of(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
        clienteRepository.saveAll(List.of(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));
        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
