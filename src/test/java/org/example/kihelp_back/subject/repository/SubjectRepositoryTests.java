package org.example.kihelp_back.subject.repository;


import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubjectRepositoryTests {
    @Autowired
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        subjectRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save subject functionality")
    public void givenSubjectObject_whenSave_theSubjectIsCreated(){
        //given
        Subject subject = DataUtils.getTestSubjectTransient();

        //when
        Subject savedSubject = subjectRepository.save(subject);

        //then
        assertThat(savedSubject).isNotNull();
        assertThat(savedSubject.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test update subject name functionality")
    public void givenSubjectToUpdate_whenSave_thenSubjectNameIsChanged(){
        //given
        String name = "Updated Subject Name";
        Subject subject = DataUtils.getTestSubjectTransient();
        //when
        subjectRepository.save(subject);
        Subject subjectToUpdate = subjectRepository.findById(subject.getId())
                .orElse(null);
        subjectToUpdate.setName(name);
        Subject updatedSubject = subjectRepository.save(subjectToUpdate);
        //then
        assertThat(updatedSubject).isNotNull();
        assertThat(updatedSubject.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Test get subject by id functionality")
    public void givenSubjectCreated_whenGetById_thenSubjectIsReturned(){
        //given
        Subject subject = DataUtils.getTestSubjectTransient();
        subjectRepository.save(subject);
        //when
        Subject obtainedSubject = subjectRepository.findById(subject.getId()).orElse(null);
        //then
        assertThat(obtainedSubject).isNotNull();
        assertThat(obtainedSubject.getName()).isEqualTo("Test Subject");
        assertThat(obtainedSubject.getCourseNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test subject not found functionality")
    public void givenSubjectIsNotCreated_whenGetById_thenOptionalIsEmpty(){
        //given

        //when
        Subject obtainedSubject = subjectRepository.findById(1).orElse(null);
        //then
        assertThat(obtainedSubject).isNull();
    }

    @Test
    @DisplayName("Test get all subject functionality")
    public void givenTwoSubjectAreStored_whenFindAll_thenAllSubjectsAreReturned(){
        //given
        Subject subject1 = DataUtils.getTestSubjectTransient();
        Subject subject2 = DataUtils.getTest2SubjectTransient();

        subjectRepository.saveAll(List.of(subject1, subject2));
        //when
        List<Subject> obtainedSubjects = subjectRepository.findAll();
        //then
        assertThat(CollectionUtils.isEmpty(obtainedSubjects)).isFalse();
    }

    @Test
    @DisplayName("Test exists subject by name and course number functionality")
    public void givenSubjectSaved_whenExistsByNameAndCourseNumber_thenReturnTrue(){
        //given
        Subject subject = DataUtils.getTestSubjectTransient();
        subjectRepository.save(subject);
        //when
        boolean existsSubject = subjectRepository.existsByNameAndCourseNumber(subject.getName(), subject.getCourseNumber());
        //then
        assertThat(existsSubject).isTrue();
    }

    @Test
    @DisplayName("Test get subjects by course number functionality")
    public void givenTwoSubjectAreStored_whenGetSubjectsByCourseNumber_thenReturnOnlyOneSubject(){
        //given
        Subject subject1 = DataUtils.getTestSubjectTransient();
        Subject subject2 = DataUtils.getTest2SubjectTransient();

        subjectRepository.saveAll(List.of(subject1, subject2));
        //when
        List<Subject> obtainedSubjects = subjectRepository.getSubjectsByCourseNumber(1);
        //then
        assertThat(CollectionUtils.isEmpty(obtainedSubjects)).isFalse();
        assertThat(obtainedSubjects.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test delete subject by id functionality")
    public void givenSubjectSaved_whenDeleteById_thenSubjectIsRemovedFromDB(){
        //given
        Subject subject = DataUtils.getTestSubjectTransient();
        subjectRepository.save(subject);
        //when
        subjectRepository.deleteById(subject.getId());
        //then
        Subject obtainedSubject = subjectRepository.findById(subject.getId()).orElse(null);
        assertThat(obtainedSubject).isNull();
    }
}