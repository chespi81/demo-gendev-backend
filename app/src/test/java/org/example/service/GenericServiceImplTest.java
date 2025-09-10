package org.example.service;

import org.example.model.BaseEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenericServiceImplTest {

    // Concrete test entity class that extends BaseEntity
    private static class TestEntity extends BaseEntity {
        private String name;

        public TestEntity() {
        }

        public TestEntity(Long id, String name) {
            setId(id);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Mock repository interface
    @Mock
    private JpaRepository<TestEntity, Long> mockRepository;

    // Concrete implementation of GenericServiceImpl for testing
    private static class TestServiceImpl extends GenericServiceImpl<TestEntity, Long, JpaRepository<TestEntity, Long>> {
        public TestServiceImpl(JpaRepository<TestEntity, Long> repository) {
            super(repository);
        }
    }

    private TestServiceImpl service;
    private TestEntity testEntity;
    private final Long testId = 1L;
    private final String testName = "Test Entity";

    @Before
    public void setUp() {
        service = new TestServiceImpl(mockRepository);
        testEntity = new TestEntity(testId, testName);
    }

    @Test
    public void testGetById_WhenEntityExists() {
        // Arrange
        when(mockRepository.findById(testId)).thenReturn(Optional.of(testEntity));

        // Act
        Optional<TestEntity> result = service.getById(testId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testEntity, result.get());
        assertEquals(testId, result.get().getId());
        assertEquals(testName, result.get().getName());
        verify(mockRepository).findById(testId);
    }

    @Test
    public void testGetById_WhenEntityDoesNotExist() {
        // Arrange
        when(mockRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<TestEntity> result = service.getById(testId);

        // Assert
        assertFalse(result.isPresent());
        verify(mockRepository).findById(testId);
    }

    @Test
    public void testSave() {
        // Arrange
        when(mockRepository.save(testEntity)).thenReturn(testEntity);

        // Act
        TestEntity result = service.save(testEntity);

        // Assert
        assertNotNull(result);
        assertEquals(testEntity, result);
        verify(mockRepository).save(testEntity);
    }

    @Test
    public void testUpdate_WhenEntityExists() {
        // Arrange
        TestEntity updatedEntity = new TestEntity();
        updatedEntity.setName("Updated Name");

        when(mockRepository.existsById(testId)).thenReturn(true);
        when(mockRepository.save(any(TestEntity.class))).thenAnswer(invocation -> {
            TestEntity savedEntity = invocation.getArgument(0);
            assertEquals(testId, savedEntity.getId());
            return savedEntity;
        });

        // Act
        Optional<TestEntity> result = service.update(testId, updatedEntity);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        assertEquals("Updated Name", result.get().getName());
        verify(mockRepository).existsById(testId);
        verify(mockRepository).save(any(TestEntity.class));
    }

    @Test
    public void testUpdate_WhenEntityDoesNotExist() {
        // Arrange
        TestEntity updatedEntity = new TestEntity();
        updatedEntity.setName("Updated Name");

        when(mockRepository.existsById(testId)).thenReturn(false);

        // Act
        Optional<TestEntity> result = service.update(testId, updatedEntity);

        // Assert
        assertFalse(result.isPresent());
        verify(mockRepository).existsById(testId);
        verify(mockRepository, never()).save(any(TestEntity.class));
    }

    @Test
    public void testDelete_WhenEntityExists() {
        // Arrange
        when(mockRepository.existsById(testId)).thenReturn(true);
        doNothing().when(mockRepository).deleteById(testId);

        // Act
        boolean result = service.delete(testId);

        // Assert
        assertTrue(result);
        verify(mockRepository).existsById(testId);
        verify(mockRepository).deleteById(testId);
    }

    @Test
    public void testDelete_WhenEntityDoesNotExist() {
        // Arrange
        when(mockRepository.existsById(testId)).thenReturn(false);

        // Act
        boolean result = service.delete(testId);

        // Assert
        assertFalse(result);
        verify(mockRepository).existsById(testId);
        verify(mockRepository, never()).deleteById(any());
    }
}
