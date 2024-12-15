package org.example.kihelp_back.subject.service;

import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.repository.SubjectRepository;
import org.example.kihelp_back.subject.service.impl.SubjectServiceImpl;
import org.example.kihelp_back.subject.util.DataUtils;
import org.example.kihelp_back.teacher.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceImplTests {
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private TeacherServiceImpl teacherService;
    @InjectMocks
    private SubjectServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test save subject functionality")
    public void givenSubjectToSave_whenSaveSubject_thenRepositoryIsCalled() {
        //given
        Subject subjectToSave = DataUtils.getTestSubjectTransient();
        BDDMockito.given(subjectRepository.existsByNameAndCourseNumber(anyString(), anyInt()))
                .willReturn(false);
        BDDMockito.given(subjectRepository.save(any(Subject.class)))
                .willReturn(DataUtils.getTestSubjectTransient());
        //when
        Subject savedSubject = serviceUnderTest.save(subjectToSave);
        //then
        assertThat(savedSubject).isNotNull();
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    @DisplayName("Test save subject with duplicate name and course number functionality")
    public void givenSubjectToSaveWithDuplicateNameAndCourseNumber_whenSaveSubject_thenExceptionIsThrown() {
        //given
        Subject subjectToSave = DataUtils.getTestSubjectTransient();
        BDDMockito.given(subjectRepository.existsByNameAndCourseNumber(anyString(), anyInt()))
                .willReturn(true);
        //when
        assertThrows(
                SubjectExistException.class, () -> serviceUnderTest.save(subjectToSave)
        );
        //then
        verify(subjectRepository, never()).save(any(Subject.class));
    }

    @Test
    @DisplayName("Test get subjects by course number functionality")
    public void givenCourseNumber_whenGetSubjectsByCourseNumber_thenRepositoryIsCalled() {
        //given
        BDDMockito.given(subjectRepository.getSubjectsByCourseNumber(anyInt()))
                .willReturn(List.of(DataUtils.getTestSubjectTransient()));
        //when
        List<Subject> obtainedSubjects = serviceUnderTest.getSubjectsByCourseNumber(1);
        //then
        assertThat(CollectionUtils.isEmpty(obtainedSubjects)).isFalse();
    }

    @Test
    @DisplayName("Test get subject by id functionality")
    public void givenId_whenGetSubjectById_thenRepositoryIsCalled() {
        //given
        BDDMockito.given(subjectRepository.findById(anyInt()))
                .willReturn(Optional.of(DataUtils.getTestSubjectPersisted()));
        //when
        Subject obtainedSubject = serviceUnderTest.getSubjectById(1);
        //then
        assertThat(obtainedSubject).isNotNull();
        assertThat(obtainedSubject.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test get subject by id functionality")
    public void givenIncorrectId_whenGetSubjectById_thenExceptionIsThrown() {
        //given
        BDDMockito.given(subjectRepository.findById(anyInt()))
                .willThrow(SubjectNotFoundException.class);
        //when
        assertThrows(
                SubjectNotFoundException.class, () -> serviceUnderTest.getSubjectById(1)
        );
        //then
    }

    @Test
    @DisplayName("Test delete subject by id functionality")
    public void givenId_whenDeleteSubject_thenRepositoryIsCalled() {
        //given
        BDDMockito.given(subjectRepository.findById(anyInt()))
                .willReturn(Optional.of(DataUtils.getTestSubjectPersisted()));
        //when
        serviceUnderTest.delete(1);
        //then
        verify(subjectRepository, times(1)).delete(DataUtils.getTestSubjectPersisted());
        verify(teacherService, times(1)).delete(anyInt());
        verify(subjectRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Test update subject functionality")
    public void givenSubjectToUpdate_whenUpdateSubject_thenRepositoryIsCalled() {
        //given
        Subject subjectToUpdate = DataUtils.getTestSubjectPersisted();
        BDDMockito.given(subjectRepository.findById(anyInt()))
                .willReturn(Optional.of(subjectToUpdate));
        BDDMockito.given(subjectRepository.save(any(Subject.class)))
                .willReturn(subjectToUpdate);
        //when
        Subject updatedSubject = serviceUnderTest.update(1, "Name");
        //then
        assertThat(updatedSubject).isNotNull();
        assertThat(updatedSubject.getName()).isEqualTo("Name");
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }
}
