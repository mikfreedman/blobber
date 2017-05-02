package io.pivotal

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.JsonParserFactory
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content


@RunWith(SpringRunner::class)
@SpringBootTest
class BlobberApplicationTests {
    lateinit var controller: MainController
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repository: BlobberFileRepository

    @Before
    fun before() {
        controller = MainController(repository)
        mockMvc = standaloneSetup(controller).build()
    }

	@Test
	fun uploadAFile() {
        val fileContents = "some file contents".toByteArray()

        val textFile = MockMultipartFile("file", "filename.txt", "text/plain", fileContents)

        val result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/files")
                .file(textFile))
                .andExpect(status().isCreated).andReturn()

        val jsonParser = JsonParserFactory.getJsonParser()
        var uuid = jsonParser.parseMap(result.response.contentAsString)["uuid"]

        mockMvc.perform(MockMvcRequestBuilders.get("/files/" + uuid))
                .andExpect(status().isOk)
                .andExpect(content().contentType("text/plain"))
                .andExpect(content().bytes(fileContents))
    }
}
