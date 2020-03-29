package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.VetModel;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static guru.springframework.sfgpetclinic.model.Speciality.in;
import static org.assertj.core.api.Assertions.assertThat;

class VetControllerTest {

    VetService vetService;

    @BeforeEach
    void setUp() {

        SpecialtyService specialtyService = new SpecialityMapService();

        vetService = new VetMapService(specialtyService);

        Vet vet1 = new Vet(1L, "Abhijit", "Ghonge", Stream.of(Speciality.in("Dogs"), Speciality.in("Cats")).collect(Collectors.toSet()));
        Vet vet2 = new Vet(2L, "John", "Thompson", Stream.of(Speciality.in("Horses"), Speciality.in("elephants")).collect(Collectors.toSet()));

        vetService.save(vet1);
        vetService.save(vet2);
    }

    @Test
    void listVets() {

        assertThat(vetService.findById(1L).getFirstName()).isEqualTo("Abhijit");
        assertThat(vetService.findById(2L).getFirstName()).isEqualTo("John");

        VetController controller = new VetController(vetService);
        controller.listVets(new VetModel());
        assertThat(controller.listVets(new VetModel())).isEqualTo("vets/index");


    }
}