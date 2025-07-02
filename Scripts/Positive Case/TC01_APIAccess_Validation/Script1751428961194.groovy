import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

println "Memulai TC01: Validasi Akses Endpoint Dasar"

// 1. Menguji GET list users
println "Menguji GET /api/users?page=1"
def responseGetList = WS.sendRequest(findTestObject('Object Repository/Web Service/Reqresin/GET_list_users'))
WS.verifyResponseStatusCode(responseGetList, 200)

// 2. Menguji GET single user
println "Menguji GET /api/users/2"
def responseGetSingle = WS.sendRequest(findTestObject('Object Repository/Web Service/Reqresin/GET_single_user'))
WS.verifyResponseStatusCode(responseGetSingle, 200)

// 3. Menguji PUT update
println "Menguji PUT /api/users/2"
def responsePut = WS.sendRequest(findTestObject('Object Repository/Web Service/Reqresin/PUT_update'))
WS.verifyResponseStatusCode(responsePut, 200)

println "TC01 Selesai: Semua endpoint dasar berhasil diakses."