package fr.esgi.cleancode.service;

import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceRemoveTest {

    @Mock
    DrivingLicenceFinderService drivingLicenceFinderService;

    @InjectMocks
    DrivingLicenceRemoveService drivingLicenceRemoveService;

    @Test
    void shouldRemovePointFromDrivingLicence() {
        // GIVEN
        final var pointsToRemoveFromDrivingLicence = 2;
        final var givenId = UUID.randomUUID();
        final var givenAvailablePoints = 12;
        final var generatedDrivingLicence = DrivingLicence
                .builder()
                .id(givenId)
                .availablePoints(givenAvailablePoints)
                .build();
        // WHEN
        final var drivingLicenceAfter = drivingLicenceRemoveService
                .removePoints(generatedDrivingLicence, pointsToRemoveFromDrivingLicence);
        final int drivingLicenceAvailablePointsAfterRemove = drivingLicenceAfter.getAvailablePoints();
        // THEN
        assertThat(drivingLicenceAvailablePointsAfterRemove)
                .isEqualTo(givenAvailablePoints - pointsToRemoveFromDrivingLicence);
    }
}