package ir.snapp.phonebook.web.resources;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import ir.snapp.phonebook.configuration.BaseIntegrationTestConfiguration;
import ir.snapp.phonebook.configuration.constants.PageFieldConstants;
import ir.snapp.phonebook.configuration.constants.TemplateRegex;
import ir.snapp.phonebook.configuration.constants.UrlMappings;
import ir.snapp.phonebook.exception.dto.ApiErrorDTO;
import ir.snapp.phonebook.exception.dto.ApiFieldErrorDTO;
import ir.snapp.phonebook.repository.jpa.ContactRepository;
import ir.snapp.phonebook.service.ContactGithubService;
import ir.snapp.phonebook.service.ContactService;
import ir.snapp.phonebook.service.dto.ContactDTO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Locale;

public class ContactResourceIntegrationTest extends BaseIntegrationTestConfiguration {

    @MockBean
    private ContactGithubService contactGithubService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @Before
    public void clearDatabase() {
        this.contactRepository.deleteAll();
    }

    @Test
    public void success_create_contact() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(faker.name().name());

        ContactDTO contactResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(ContactDTO.Fields.id, Matchers.notNullValue())
                .extract().body().as(ContactDTO.class);

        Assert.assertTrue(this.contactRepository.existsById(contactResponse.getId()));
    }

    @Test
    public void fail_create_contact_when_id_is_not_null() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(faker.number().randomNumber());
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.id));
    }

    @Test
    public void fail_create_contact_when_name_is_null() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(null);
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.name));
    }

    @Test
    public void fail_create_contact_when_email_is_null() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(null);
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.email));
    }

    @Test
    public void fail_create_contact_when_email_is_invalid() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.name().fullName()); // invalid emailAddress
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.email));
    }

    @Test
    public void fail_create_contact_when_organization_is_null() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(null);
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.organization));
    }

    @Test
    public void fail_create_contact_when_phone_number_is_null() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(null);
        contactDTO.setGithub(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.phoneNumber));
    }

    @Test
    public void fail_create_contact_when_phone_number_is_invalid() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.name().fullName()); // invalid phoneNumber
        contactDTO.setGithub(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.phoneNumber));
    }

    @Test
    public void fail_create_contact_when_github_is_null() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(null);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.github));
    }


    @Test
    public void fail_create_contact_when_contact_is_exist() {
        Mockito.doNothing().when(contactGithubService).save(Mockito.anyString(), Mockito.any());

        Faker faker = new Faker(Locale.US);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(faker.name().fullName());
        contactDTO.setEmail(faker.internet().emailAddress());
        contactDTO.setOrganization(faker.company().name());
        contactDTO.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        contactDTO.setGithub(faker.name().name());

        ContactDTO contactResponse = this.contactService.save(contactDTO);

        Assert.assertTrue(this.contactRepository.existsById(contactResponse.getId()));

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .when()
                .put(UrlMappings.CONTACT_CREATE)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.error, Matchers.equalTo("DatabaseException"));
    }

    @Test
    public void success_search_contact_when_all_field_is_null() {
        Faker faker = new Faker(Locale.US);

        ContactDTO firstContact = new ContactDTO();
        firstContact.setName(faker.name().fullName());
        firstContact.setEmail(faker.internet().emailAddress());
        firstContact.setOrganization(faker.company().name());
        firstContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        firstContact.setGithub(faker.name().name());

        firstContact = this.contactService.save(firstContact);

        Assert.assertTrue(this.contactRepository.existsById(firstContact.getId()));

        ContactDTO secondContact = new ContactDTO();
        secondContact.setName(faker.name().fullName());
        secondContact.setEmail(faker.internet().emailAddress());
        secondContact.setOrganization(faker.company().name());
        secondContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        secondContact.setGithub(faker.name().name());

        secondContact = this.contactService.save(secondContact);

        Assert.assertTrue(this.contactRepository.existsById(secondContact.getId()));

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setName(null);
        searchQuery.setEmail(null);
        searchQuery.setOrganization(null);
        searchQuery.setPhoneNumber(null);
        searchQuery.setGithub(null);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(PageFieldConstants.NUMBER_OF_ELEMENTS, Matchers.equalTo(2))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.id, Matchers.notNullValue())
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.name, Matchers.hasItems(Matchers.equalTo(firstContact.getName()), Matchers.equalTo(secondContact.getName())))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.email, Matchers.hasItems(firstContact.getEmail(), secondContact.getEmail()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.phoneNumber, Matchers.hasItems(firstContact.getPhoneNumber(), secondContact.getPhoneNumber()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.organization, Matchers.hasItems(firstContact.getOrganization(), secondContact.getOrganization()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.github, Matchers.hasItems(firstContact.getGithub(), secondContact.getGithub()));
    }

    @Test
    public void success_search_contact_when_name_is_specified() {
        Faker faker = new Faker(Locale.US);

        ContactDTO firstContact = new ContactDTO();
        firstContact.setName(faker.name().fullName());
        firstContact.setEmail(faker.internet().emailAddress());
        firstContact.setOrganization(faker.company().name());
        firstContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        firstContact.setGithub(faker.name().name());

        firstContact = this.contactService.save(firstContact);

        Assert.assertTrue(this.contactRepository.existsById(firstContact.getId()));

        ContactDTO secondContact = new ContactDTO();
        secondContact.setName(faker.name().fullName());
        secondContact.setEmail(faker.internet().emailAddress());
        secondContact.setOrganization(faker.company().name());
        secondContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        secondContact.setGithub(faker.name().name());

        secondContact = this.contactService.save(secondContact);

        Assert.assertTrue(this.contactRepository.existsById(secondContact.getId()));

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setName(firstContact.getName());
        searchQuery.setEmail(null);
        searchQuery.setOrganization(null);
        searchQuery.setPhoneNumber(null);
        searchQuery.setGithub(null);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(PageFieldConstants.NUMBER_OF_ELEMENTS, Matchers.equalTo(1))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.id, Matchers.notNullValue())
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.name, Matchers.hasItem(firstContact.getName()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.email, Matchers.hasItem(firstContact.getEmail()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.phoneNumber, Matchers.hasItem(firstContact.getPhoneNumber()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.organization, Matchers.hasItem(firstContact.getOrganization()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.github, Matchers.hasItem(firstContact.getGithub()));
    }

    @Test
    public void success_search_contact_when_email_is_specified() {
        Faker faker = new Faker(Locale.US);

        ContactDTO firstContact = new ContactDTO();
        firstContact.setName(faker.name().fullName());
        firstContact.setEmail(faker.internet().emailAddress());
        firstContact.setOrganization(faker.company().name());
        firstContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        firstContact.setGithub(faker.name().name());

        firstContact = this.contactService.save(firstContact);

        Assert.assertTrue(this.contactRepository.existsById(firstContact.getId()));

        ContactDTO secondContact = new ContactDTO();
        secondContact.setName(faker.name().fullName());
        secondContact.setEmail(faker.internet().emailAddress());
        secondContact.setOrganization(faker.company().name());
        secondContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        secondContact.setGithub(faker.name().name());

        secondContact = this.contactService.save(secondContact);

        Assert.assertTrue(this.contactRepository.existsById(secondContact.getId()));

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setName(null);
        searchQuery.setEmail(firstContact.getEmail());
        searchQuery.setOrganization(null);
        searchQuery.setPhoneNumber(null);
        searchQuery.setGithub(null);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(PageFieldConstants.NUMBER_OF_ELEMENTS, Matchers.equalTo(1))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.id, Matchers.notNullValue())
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.name, Matchers.hasItem(firstContact.getName()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.email, Matchers.hasItem(firstContact.getEmail()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.phoneNumber, Matchers.hasItem(firstContact.getPhoneNumber()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.organization, Matchers.hasItem(firstContact.getOrganization()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.github, Matchers.hasItem(firstContact.getGithub()));
    }

    @Test
    public void success_search_contact_when_organization_is_specified() {
        Faker faker = new Faker(Locale.US);

        ContactDTO firstContact = new ContactDTO();
        firstContact.setName(faker.name().fullName());
        firstContact.setEmail(faker.internet().emailAddress());
        firstContact.setOrganization(faker.company().name());
        firstContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        firstContact.setGithub(faker.name().name());

        firstContact = this.contactService.save(firstContact);

        Assert.assertTrue(this.contactRepository.existsById(firstContact.getId()));

        ContactDTO secondContact = new ContactDTO();
        secondContact.setName(faker.name().fullName());
        secondContact.setEmail(faker.internet().emailAddress());
        secondContact.setOrganization(faker.company().name());
        secondContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        secondContact.setGithub(faker.name().name());

        secondContact = this.contactService.save(secondContact);

        Assert.assertTrue(this.contactRepository.existsById(secondContact.getId()));

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setName(null);
        searchQuery.setEmail(null);
        searchQuery.setOrganization(firstContact.getOrganization());
        searchQuery.setPhoneNumber(null);
        searchQuery.setGithub(null);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(PageFieldConstants.NUMBER_OF_ELEMENTS, Matchers.equalTo(1))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.id, Matchers.notNullValue())
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.name, Matchers.hasItem(firstContact.getName()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.email, Matchers.hasItem(firstContact.getEmail()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.phoneNumber, Matchers.hasItem(firstContact.getPhoneNumber()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.organization, Matchers.hasItem(firstContact.getOrganization()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.github, Matchers.hasItem(firstContact.getGithub()));
    }

    @Test
    public void success_search_contact_when_phone_number_is_specified() {
        Faker faker = new Faker(Locale.US);

        ContactDTO firstContact = new ContactDTO();
        firstContact.setName(faker.name().fullName());
        firstContact.setEmail(faker.internet().emailAddress());
        firstContact.setOrganization(faker.company().name());
        firstContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        firstContact.setGithub(faker.name().name());

        firstContact = this.contactService.save(firstContact);

        Assert.assertTrue(this.contactRepository.existsById(firstContact.getId()));

        ContactDTO secondContact = new ContactDTO();
        secondContact.setName(faker.name().fullName());
        secondContact.setEmail(faker.internet().emailAddress());
        secondContact.setOrganization(faker.company().name());
        secondContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        secondContact.setGithub(faker.name().name());

        secondContact = this.contactService.save(secondContact);

        Assert.assertTrue(this.contactRepository.existsById(secondContact.getId()));

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setName(null);
        searchQuery.setEmail(null);
        searchQuery.setOrganization(null);
        searchQuery.setPhoneNumber(firstContact.getPhoneNumber());
        searchQuery.setGithub(null);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(PageFieldConstants.NUMBER_OF_ELEMENTS, Matchers.equalTo(1))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.id, Matchers.notNullValue())
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.name, Matchers.hasItem(firstContact.getName()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.email, Matchers.hasItem(firstContact.getEmail()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.phoneNumber, Matchers.hasItem(firstContact.getPhoneNumber()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.organization, Matchers.hasItem(firstContact.getOrganization()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.github, Matchers.hasItem(firstContact.getGithub()));
    }

    @Test
    public void success_search_contact_when_github_is_specified() {
        Faker faker = new Faker(Locale.US);

        ContactDTO firstContact = new ContactDTO();
        firstContact.setName(faker.name().fullName());
        firstContact.setEmail(faker.internet().emailAddress());
        firstContact.setOrganization(faker.company().name());
        firstContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        firstContact.setGithub(faker.name().name());

        firstContact = this.contactService.save(firstContact);

        Assert.assertTrue(this.contactRepository.existsById(firstContact.getId()));

        ContactDTO secondContact = new ContactDTO();
        secondContact.setName(faker.name().fullName());
        secondContact.setEmail(faker.internet().emailAddress());
        secondContact.setOrganization(faker.company().name());
        secondContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        secondContact.setGithub(faker.name().name());

        secondContact = this.contactService.save(secondContact);

        Assert.assertTrue(this.contactRepository.existsById(secondContact.getId()));

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setName(null);
        searchQuery.setEmail(null);
        searchQuery.setOrganization(null);
        searchQuery.setPhoneNumber(null);
        searchQuery.setGithub(firstContact.getGithub());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(PageFieldConstants.NUMBER_OF_ELEMENTS, Matchers.equalTo(1))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.id, Matchers.notNullValue())
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.name, Matchers.hasItem(firstContact.getName()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.email, Matchers.hasItem(firstContact.getEmail()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.phoneNumber, Matchers.hasItem(firstContact.getPhoneNumber()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.organization, Matchers.hasItem(firstContact.getOrganization()))
                .body(PageFieldConstants.CONTENT + DOT + ContactDTO.Fields.github, Matchers.hasItem(firstContact.getGithub()));
    }

    @Test
    public void success_search_contact_when_search_query_is_invalid_then_return_empty() {
        Faker faker = new Faker(Locale.US);

        ContactDTO firstContact = new ContactDTO();
        firstContact.setName(faker.name().fullName());
        firstContact.setEmail(faker.internet().emailAddress());
        firstContact.setOrganization(faker.company().name());
        firstContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        firstContact.setGithub(faker.name().name());

        firstContact = this.contactService.save(firstContact);

        Assert.assertTrue(this.contactRepository.existsById(firstContact.getId()));

        ContactDTO secondContact = new ContactDTO();
        secondContact.setName(faker.name().fullName());
        secondContact.setEmail(faker.internet().emailAddress());
        secondContact.setOrganization(faker.company().name());
        secondContact.setPhoneNumber(faker.regexify(TemplateRegex.FAKER_CELLPHONE));
        secondContact.setGithub(faker.name().name());

        secondContact = this.contactService.save(secondContact);

        Assert.assertTrue(this.contactRepository.existsById(secondContact.getId()));

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setName(faker.number().digit());
        searchQuery.setEmail(faker.number().digit());
        searchQuery.setOrganization(faker.number().digit());
        searchQuery.setPhoneNumber(faker.number().digit());
        searchQuery.setGithub(faker.number().digit());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(PageFieldConstants.NUMBER_OF_ELEMENTS, Matchers.equalTo(0));
    }

    @Test
    public void fail_search_contact_when_id_not_null() {
        Faker faker = new Faker(Locale.US);

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setId(faker.number().randomNumber());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.id));
    }

    @Test
    public void fail_search_contact_when_phone_number_is_string() {
        Faker faker = new Faker(Locale.US);

        ContactDTO searchQuery = new ContactDTO();
        searchQuery.setPhoneNumber(faker.name().name());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(searchQuery)
                .when()
                .post(UrlMappings.CONTACT_SEARCH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body(ApiErrorDTO.Fields.fields + DOT + ApiFieldErrorDTO.Fields.field, Matchers.hasItem(ContactDTO.Fields.phoneNumber));
    }

}
