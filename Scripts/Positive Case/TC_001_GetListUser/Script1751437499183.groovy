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

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import groovy.json.JsonSlurper

KeywordUtil.logInfo("Memulai TC_001: Get List Users dengan Verifikasi Lengkap")

'Kirim Request'
def response = WS.sendRequest(findTestObject('Object Repository/Web Service/Reqresin/GET_list_users'))

'Check Status & Header'
KeywordUtil.logInfo("Memverifikasi status code dan header...")
WS.verifyResponseStatusCode(response, 200)
String contentTypeHeader = response.getHeaderField('Content-Type')
assert contentTypeHeader.contains('application/json')
KeywordUtil.logInfo("✔️ Status Code dan Content-Type sudah sesuai.")

'Parse JSON'
def jsonSlurper = new JsonSlurper()
def parsedResponse = jsonSlurper.parseText(response.getResponseText())

'Verifikasi Struktur Utama JSON secara Manual'
KeywordUtil.logInfo("Memverifikasi struktur utama JSON...")
assert parsedResponse.containsKey('page')
assert parsedResponse.containsKey('per_page')
assert parsedResponse.containsKey('total')
assert parsedResponse.containsKey('total_pages')
assert parsedResponse.containsKey('data')
assert parsedResponse.containsKey('support')
KeywordUtil.logInfo("Struktur utama JSON sudah sesuai.")

'Verifikasi Data Paginasi'
KeywordUtil.logInfo("Memverifikasi data paginasi...")
assert parsedResponse.page == 1
assert parsedResponse.per_page == 6
KeywordUtil.logInfo("Data paginasi sudah sesuai.")

'Verifikasi Detail Isi Data (Looping)'
KeywordUtil.logInfo("Memverifikasi detail setiap user di dalam array 'data'...")
def userList = parsedResponse.data

assert userList != null && userList.size() > 0
KeywordUtil.logInfo("Ditemukan ${userList.size()} user di dalam data.")

userList.eachWithIndex { user, index ->
	KeywordUtil.logInfo("--> Memeriksa User ke-${index + 1}")
	
	assert user.containsKey('id') && user.id instanceof Integer
	assert user.containsKey('email') && user.email instanceof String && user.email.contains('@')
	assert user.containsKey('first_name') && user.first_name instanceof String
}
KeywordUtil.logInfo("Semua user dalam array 'data' telah terverifikasi.")

'Mengambil data spesifik dari user pertama'
def FirstUserCheck = userList[3]
def userId = FirstUserCheck.id
def userEmail = FirstUserCheck.email
GlobalVariable.id = userId

'Data Spesifik User Pertama:'
KeywordUtil.logInfo("ID    : " + userId)
KeywordUtil.logInfo("Email : " + userEmail)

KeywordUtil.logInfo("Semua user dalam array 'data' telah terverifikasi.")