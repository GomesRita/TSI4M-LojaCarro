package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarroService carroService;
    private Class<? extends Throwable> ItemNotFoundException;

    @Test
    void saveDeveDelegarParaRepositoryERetornarCarroSalvo() {
        Carro carro = criarCarro(1L, "Gol", 2020);

        Mockito.when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.save(carro);

        Assertions.assertSame(carro, resultado);
        Mockito.verify(carroRepository).save(carro);
        Mockito.verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void updateDeveDelegarParaRepositoryERetornarCarroAtualizado() {
        Carro carro = criarCarro(2L, "Onix", 2022);

        Mockito.when(carroRepository.save(carro)).thenReturn(carro);

        Carro resultado = carroService.update(carro);

        Assertions.assertSame(carro, resultado);
        Mockito.verify(carroRepository).save(carro);
        Mockito.verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void deleteByIdDeveDelegarParaRepositoryComIdInformado() {
        Long id = 10L;

        carroService.deleteById(id);

        Mockito.verify(carroRepository, Mockito.times(1)).deleteById(id);
        Mockito.verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findByIdDeveRetornarCarroQuandoEncontrado() {
        Long id = 3L;
        Carro carro = criarCarro(id, "HB20", 2021);

        Mockito.when(carroRepository.findById(id)).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = carroService.findById(id);

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertSame(carro, resultado.get());
        Mockito.verify(carroRepository).findById(id);
        Mockito.verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findByIdDeveRetornarOptionalVazioQuandoNaoEncontrado() {
        Long id = 99L;

        Mockito.when(carroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Carro> resultado = carroService.findById(id);

        Assertions.assertTrue(resultado.isEmpty());
        Mockito.verify(carroRepository).findById(id);
        Mockito.verifyNoMoreInteractions(carroRepository);
    }

    @Test
    void findAllDeveRetornarListaDeCarrosDoRepository() {
        List<Carro> carros = List.of(
                criarCarro(1L, "Gol", 2020),
                criarCarro(2L, "Onix", 2022)
        );

        Mockito.when(carroRepository.findAll()).thenReturn(carros);

        List<Carro> resultado = carroService.findAll();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertSame(carros, resultado);
        Mockito.verify(carroRepository).findAll();
        Mockito.verifyNoMoreInteractions(carroRepository);
    }


    //Implementa Modelo Vazio
    @Test
    void deveLancarExcecaoQuandoModeloForNulo() {
        Assertions.assertThrows(CampoInvalido.class, () -> criarCarro(17L, null, 2010));
    }


    @Test
    void deveLancarExcecaoQuandoModeloForVazio() {
        Assertions.assertThrows(CampoInvalido.class, () -> criarCarro(17L, "", 2012));
    }

    @Test
    void deveLancarExcecaoQuandoAnoForInvalido() {
        Assertions.assertThrows(CampoInvalido.class, () -> criarCarro(17L, "HB20", 1799));
    }


    private Carro criarCarro(Long id, String modelo, int ano) {
        if (modelo == null || modelo.isBlank()) {
            throw new CampoInvalido("modelo");
        }
        if (ano < 1886 || ano > LocalDate.now().getYear()) {
            throw new CampoInvalido("ano");
        }
        return new Carro(id, modelo, ano);

    }
}


































































