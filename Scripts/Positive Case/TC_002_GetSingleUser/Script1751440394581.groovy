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
import groovy.json.JsonSlurper
import internal.GlobalVariable

KeywordUtil.logInfo("Memulai Tes: Get Single User dengan Verifikasi Lengkap")

'Kirim Request'
def response = WS.sendRequest(findTestObject('Object Repository/Web Service/ReqresIn/GET_single_user'))

'Check Status & Header'
KeywordUtil.logInfo("Memverifikasi status code dan header...")
WS.verifyResponseStatusCode(response, 200)
String contentTypeHeader = response.getHeaderField('Content-Type')
assert contentTypeHeader.contains('application/json')
KeywordUtil.logInfo("Status Code dan Content-Type sudah sesuai.")

'Parse JSON'
def jsonSlurper = new JsonSlurper()
def parsedResponse = jsonSlurper.parseText(response.getResponseText())

'Get object data' 
def user = parsedResponse.data

'Check  object data ada sebelum melanjutkan'
assert user != null : "Object 'data' tidak ditemukan dalam respons."
KeywordUtil.logInfo("Object 'data' berhasil di-parse.")

'Verifikasi Struktur & Data Detail User'
KeywordUtil.logInfo("Memverifikasi detail data user...")

//Verifikasi keberadaan field 
assert user.containsKey('id')
assert user.containsKey('email')
assert user.containsKey('first_name')
assert user.containsKey('last_name')

//Verifikasi nilai, tipe data, dan format
// Ganti angka '4' di bawah ini jika Anda mengetes ID user yang berbeda
assert user.id == 4 : "ID User yang didapat tidak sesuai. Diharapkan 4, tapi didapat ${user.id}"
assert user.id instanceof Integer : "Tipe data ID harus Integer"
assert user.email instanceof String : "Tipe data Email harus String"
assert user.email.contains('@') : "Format email tidak valid, tidak ada karakter '@'"

KeywordUtil.logInfo("Detail data user (ID, Tipe Data, Format) telah terverifikasi.")
KeywordUtil.logInfo("ID: ${user.id}, Email: ${user.email}, Nama: ${user.first_name}")


//Simpan data ke Global Variable untuk digunakan tes lain
GlobalVariable.dynamic_email = user.email
KeywordUtil.logInfo("Email '${user.email}' telah berhasil disimpan ke GlobalVariable.")

KeywordUtil.logInfo("Verifikasi Get Single User berhasil.")