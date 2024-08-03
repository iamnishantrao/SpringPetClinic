package org.example.webapp.bootstrap;

import org.example.data.model.*;
import org.example.data.services.OwnerService;
import org.example.data.services.PetTypeService;
import org.example.data.services.SpecialityService;
import org.example.data.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCat = petTypeService.save(cat);

        System.out.println("Loaded pet types...");

        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setAddress("123 Main Street");
        owner1.setCity("San Francisco");
        owner1.setTelephone("1234567890");

        Pet owner1Pet = new Pet();
        owner1Pet.setPetType(savedDog);
        owner1Pet.setOwner(owner1);
        owner1Pet.setBirthDate(LocalDate.now());
        owner1Pet.setName("Rosco");
        owner1.getPets().add(owner1Pet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Doe");
        owner2.setAddress("123 Market Street");
        owner2.setCity("San Francisco");
        owner2.setTelephone("0123456789");

        Pet owner2Pet = new Pet();
        owner2Pet.setPetType(savedCat);
        owner2Pet.setOwner(owner2);
        owner2Pet.setBirthDate(LocalDate.now());
        owner2Pet.setName("Cat");
        owner2.getPets().add(owner2Pet);

        ownerService.save(owner2);

        System.out.println("Loaded owners and pets...");

        Speciality speciality1 = new Speciality();
        speciality1.setDescription("Radiology");

        Speciality savedSpeciality1 = specialityService.save(speciality1);

        Speciality speciality2 = new Speciality();
        speciality2.setDescription("Surgery");

        Speciality savedSpeciality2 = specialityService.save(speciality2);

        Speciality speciality3 = new Speciality();
        speciality3.setDescription("Dentistry");

        Speciality savedSpeciality3 = specialityService.save(speciality3);

        Vet vet1 = new Vet();
        vet1.setFirstName("Adam");
        vet1.setLastName("Rich");
        vet1.getSpecialities().add(savedSpeciality1);
        vet1.getSpecialities().add(savedSpeciality2);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Bob");
        vet2.setLastName("Rich");
        vet2.getSpecialities().add(savedSpeciality3);

        vetService.save(vet2);

        System.out.println("Loaded vets and specialities...");
    }
}
