package fr.esgi.cleancode.service;

import fr.esgi.cleancode.exception.InvalidDrivingLicenceException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceReturnTest {

    @InjectMocks
    private DrivingLicenceCheckReturnService drivingLicenceCheckReturnService;

    @Mock
    private DrivingLicenceSaverService drivingLicenceSaverService;

    @Test
    public void shouldReturnDrivingLicencWhenIsSaved() {
        // GIVEN
        final var givenId = UUID.randomUUID();
        final var givenSocialSecurityNumber = "123456789123456";
        final var givenAvailablePoints = 12;
        final DrivingLicence randomDrivingLicence = DrivingLicence
                .builder()
                .id(givenId)
                .availablePoints(givenAvailablePoints)
                .driverSocialSecurityNumber(givenSocialSecurityNumber)
                .build();
        // WHEN & THEN
        assertThatNoException()
                .isThrownBy(() -> drivingLicenceCheckReturnService
                        .checkReturnedDrivingLicence(givenId, randomDrivingLicence));
    }

    @Test
    public void shouldThrowExceptionIfInvalidDrivingLicenceWhileTryingToSave() {
        // GIVEN
        final var givenId = UUID.randomUUID();
        final var givenSocialSecurityNumber = "uwu";
        final var givenAvailablePoints = 15;
        final DrivingLicence givenDrivingLicence = DrivingLicence
                .builder()
                .id(givenId)
                .availablePoints(givenAvailablePoints)
                .driverSocialSecurityNumber(givenSocialSecurityNumber)
                .build();
        // WHEN (STUB)
        doThrow(InvalidDrivingLicenceException.class)
                .when(drivingLicenceSaverService)
                .saveDrivingLicence(givenId, givenDrivingLicence);

        // THEN
        assertThatException().isThrownBy(() -> drivingLicenceCheckReturnService
                .checkReturnedDrivingLicence(givenId, givenDrivingLicence));
    }

}