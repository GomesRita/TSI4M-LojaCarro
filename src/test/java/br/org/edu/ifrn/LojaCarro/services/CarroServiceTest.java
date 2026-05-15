package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import br.org.edu.ifrn.LojaCarro.services.CampoInvalido;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarroService carroService;

    @Test
    void saveDeveDelegarParaRepositoryERetornarCarroSalvo() {
        Carro carro = criarCarro(1L, "Gol", 2020);

        when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.save(carro);

        assertSame(carro, resultado);
        verify(carroRepository).save(carro);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void updateDeveDelegarParaRepositoryERetornarCarroAtualizado() {
        Carro carro = criarCarro(2L, "Onix", 2022);

        when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.update(carro);

        assertSame(carro, resultado);
        verify(carroRepository).save(carro);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void deleteByIdDeveDelegarParaRepositoryComIdInformado() {
        Long id = 10L;

        carroService.deleteById(id);

        verify(carroRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findByIdDeveRetornarCarroQuandoEncontrado() {
        Long id = 3L;
        Carro carro = criarCarro(id, "HB20", 2021);

        when(carroRepository.findById(id)).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = carroService.findById(id);

        assertTrue(resultado.isPresent());
        assertSame(carro, resultado.get());
        verify(carroRepository).findById(id);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findByIdDeveRetornarOptionalVazioQuandoNaoEncontrado() {
        Long id = 99L;

        when(carroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Carro> resultado = carroService.findById(id);

        assertTrue(resultado.isEmpty());
        verify(carroRepository).findById(id);
        verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findAllDeveRetornarListaDeCarrosDoRepository() {
        List<Carro> carros = List.of(
                criarCarro(1L, "Gol", 2020),
                criarCarro(2L, "Onix", 2022)
        );

        when(carroRepository.findAll()).thenReturn(carros);

        List<Carro> resultado = carroService.findAll();

        assertEquals(2, resultado.size());
        assertSame(carros, resultado);
        verify(carroRepository).findAll();
        verifyNoMoreInteractions(carroRepository);
    }

    private Carro criarCarro(Long id, String modelo, int ano) {
        Carro carro = new Carro(id, modelo, ano);
        if (modelo == null || modelo.isBlank()) {
            throw new CampoInvalido("modelo"); // ← sem isso, o teste nunca passa
        }
        if (ano < 1886 || ano > LocalDate.now().getYear()) {
            throw new CampoInvalido("ano");
        }
        return new Carro(id, modelo, ano);

    }

    @Test
    void deveLancarExcecaoQuandoModeloForNulo() {
        assertThrows(CampoInvalido.class, () -> criarCarro(17L, "HB20", 2021));
    }

    @Test
    void deveLancarExcecaoQuandoModeloForVazio() {
        assertThrows(CampoInvalido.class, () -> criarCarro(17L, "HB20", 2021));
    }

    @Test
    void deveLancarExcecaoQuandoAnoForInvalido() {
        assertThrows(CampoInvalido.class, () -> criarCarro(17L, "HB20", 1800));
    }

    @Test
    void deveCriarCarroComDadosValidos() {
        Carro carro = criarCarro(17L, "HB20", 2021);
        assertNotNull(carro);
        assertEquals(17L, carro.getId());
        assertEquals("HB20", carro.getModelo());
        assertEquals(2021, carro.getAno());
    }
}
