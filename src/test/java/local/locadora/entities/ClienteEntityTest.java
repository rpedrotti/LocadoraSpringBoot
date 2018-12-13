
package local.locadora.entities;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import local.locadora.exceptions.ClienteException;
import local.locadora.exceptions.FilmeException;
import local.locadora.exceptions.LocacaoException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class ClienteEntityTest {

    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Note que <b>validator</b> aplica a validação do bean validation
     * O Iterator é utilizado para pegar as violações ocorridas
     */
    @Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void cpfDeveSerValido(){
        expected.expect(ClienteException.class);
        expected.expectMessage("O CPF não é válido");
        Cliente cliente = new Cliente();
        cliente.setCpf("01824390aa3");
    }
    
    @Test
    public void nomeClienteNaoDeveAceitarCaracteresEspeciais(){
        expected.expect(ClienteException.class);
        expected.expectMessage("Nome não deve possuir símbolos ou números");
        Cliente cliente = new Cliente();
        cliente.setNome("J0rge Silva");
        
    }
    @Test
    public void naoDeveRegistrarNomeComEspacoInicioEFim(){
        Cliente cliente = new Cliente();
        cliente.setNome(" Rodrigo Pedrotti ");
        assertThat(cliente.getNome(),is("Rodrigo Pedrotti"));
    }
    /*@Test
    public void nomeSobrenomeClienteDeveSerLetraMaiuscula(){
        Cliente cliente = new Cliente();
        cliente.setNome("rodrigo pedrotti");
        assertThat(cliente.getNome(),is("Rodrigo Pedrotti"));
    }*/
    @Test
    public void naoDeveRegistrarFilmeComEspacoInicioEFim(){
        Filme filme = new Filme();
        filme.setNome(" Narnia ");
        assertThat(filme.getNome(),is("Narnia"));
    }
    @Test
    public void nomeFilmeDeveTerEntre2a100CaracteresInclusive(){
        expected.expect(FilmeException.class);
        expected.expectMessage("Um filme deve possuir entre 2 e 100 caracteres");
        Filme filme = new Filme();
        filme.setNome("N");
    }
    @Test
    public void estoqueDoFilmeNaoPodeSerNegativo(){
        expected.expect(FilmeException.class);
        expected.expectMessage("O Estoque deve ser positivo");
        Filme filme = new Filme();
        filme.setEstoque(-40);
    }
    @Test
    public void valorDaLocacaoNaoPodeUltrapassarDoisDigitos(){
        expected.expect(FilmeException.class);
        expected.expectMessage("O Preço deve ter no máximo dois dígitos");
        Filme filme = new Filme();
        filme.setPrecoLocacao(199.99);   
    }
    
    @Test
    public void valorDaLocacaoNaoPodeSerNegativo(){
        expected.expect(FilmeException.class);
        expected.expectMessage("O Valor da locação deve ser positivo");
        Filme filme = new Filme();
        filme.setPrecoLocacao(-0.01);
    }
    @Test
    public void locacaoNaoPoderaSerFeitaSemCliente(){
        expected.expect(LocacaoException.class);
        expected.expectMessage("Um cliente deve ser selecionado");
        Locacao locacao = new Locacao();
        locacao.setCliente(null);
    }
    /* @Test
    public void locacaoDevePossuirPeloMenosUmFilme(){
        expected.expect(LocacaoException.class);
        expected.expectMessage("Pelo menos um filme deve ser selecionado");
        Locacao locacao = new Locacao();
        locacao.setFilmes(0);
    }*/
    @Test
    public void locacaoNaoPoderaSerFeitaSemDataLocacao(){
        expected.expect(LocacaoException.class);
        expected.expectMessage("A data de locação não deve ser nula");
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(null);
    }
      @Test
    public void locacaoNaoPoderaSerFeitaSemDataDevolucao(){
        expected.expect(LocacaoException.class);
        expected.expectMessage("A data de retorno não deve ser nula");
        Locacao locacao = new Locacao();
        locacao.setDataRetorno(null);
    }
    @Test
    public void locacaoValorDaLocacaoNaoPodeUltrapassarDoisDigitos(){
        expected.expect(LocacaoException.class);
        expected.expectMessage("O Preço deve ter no máximo dois dígitos");
        Locacao locacao = new Locacao();
        locacao.setValor(99.999);   
    }
     @Test
    public void locacaoValorDaLocacaoNaoPodeSerNegativo(){
        expected.expect(LocacaoException.class);
        expected.expectMessage("O valor da locação deve ser positivo");
        Locacao locacao = new Locacao();
        locacao.setValor(-0.01);
    }
}

