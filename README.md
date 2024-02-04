# OTPCompose
`OTPTextField` is a composable function designed for creating One-Time Password (OTP) input fields in Jetpack Compose. It supports both outlined and underlined styles for individual digit containers, providing flexibility for UI customization.

### Installation
Make sure your project is set up to use Jetpack Compose. If not, refer to the [official documentation](https://developer.android.com/jetpack/compose) for installation instructions.

To use `OTPTextField` in your Android project, follow these steps:

1. Add the following repository to your `settings.gradle` file:
   #### `settings.gradle`

    ```groovy
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            maven { url 'https://jitpack.io' } // Add This Line
        }
    }
    ```


2. Add below dependency to your app-level `build.gradle` or to `build.gradle.kts` file if you are using Kotlin DSL:
   #### Groovy

   ```groovy
   dependencies {
       implementation 'com.prabhatpandey:otp-compose:VERSION'
   }
   ```

   #### Kotlin DSL

   ```kotlin
   dependencies {
       implementation("com.prabhatpandey:otp-compose:VERSION")
   }
   ```

Replace  `VERSION` with the desired release tag.


### Usage

1. Add the `OTPTextField` composable to your MainActivity.

   ```kotlin
   // Import Statements

   class MainActivity : ComponentActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContent {
               
               var otp by remember {
                   mutableStateOf("")
               }
   
               MyAppTheme {
                   // Use OTPTextField in your UI
                   Surface(
                       modifier = Modifier.fillMaxSize(),
                       color = Color.White
                   ) {
                       OTPTextField(
                           value = otp, // Initial value
                           onTextChanged = { otp = it },
                           numDigits = 4, // Number of digits in OTP
                           isMasked = true, // Mask digits for security
                           digitContainerStyle = OtpTextFieldDefaults.outlinedContainer(), // Choose style (outlined or underlined)
                           textStyle = MaterialTheme.typography.titleLarge, // Configure text style
                           isError = false // Indicate whether the OTP field is in an error state
                       )
                   }
               }
           }
       }
   }
   ```

2. Customize the OTPTextField parameters as needed:

    - `value`: The current value of the OTP text field.
    - `onTextChanged`: A callback triggered when the text in the OTP field changes.
    - `numDigits`: The number of digits for the OTP. Default is 4. Can only be in range of 4-6.
    - `isMasked`: Whether to mask the digits in the OTP field. Default is false.
    - `digitContainerStyle`: The style configuration for the digit containers (outlined or underlined).
    - `textStyle`: The style configuration for the text within the digit containers.
    - `isError`: Whether the OTP field is in an error state.
    - `mask`: Provide your own composable to be used as masked digit. Keep null to use default.

3. Run your application to see the OTPTextField in action!

### Contributions Welcome!
Contributions to this project are welcome and encouraged. If you have suggestions, feature requests, or bug reports, please feel free to open an issue or submit a pull request.

### Thank You!
Thank you for using OTPTextField! We hope it enhances your Jetpack Compose development experience. If you encounter any issues or have feedback, don't hesitate to reach out. Happy coding!

### License
This library is distributed under the MIT license. See the LICENSE file for more details.

## Author
[<img src="https://raw.githubusercontent.com/prabhatsdp/prabhatsdp/3f4612b597a603573c215ab3b54e38ad944b7e19/assets/banner.svg" alt="👋 Prabhat Pandey | Android App Developerl)" title="Prabhat Pandey" width="100%"/>](https://prabhatpandey.com/)
### 📫 How to reach me: 

<a href="https://www.linkedin.com/in/prabhatsdp/" target="_blank"><img src="https://img.shields.io/badge/LinkedIn-%230077B5.svg?&style=rounded&logo=linkedin&logoColor=white" alt="LinkedIn" height="32px"></a>
<a href="https://github.com/prabhatsdp/" target="_blank"><img src="https://img.shields.io/badge/Github-%23121011.svg?&style=rounded&logo=github&logoColor=white" alt="Github" height="32px"></a>
<a href="https://twitter.com/prabhatsdp/" target="_blank"><img src="https://img.shields.io/badge/Twitter-%231DA1F2.svg?&style=rounded&logo=twitter&logoColor=white" alt="Twitter" height="32px"></a>
<a href="https://wa.me/917651894906" target="_blank"><img src="https://img.shields.io/badge/WhatsApp-%23075E54,.svg?&style=rounded&logo=whatsapp&logoColor=white" alt="WhatsApp" height="32px"></a>
<a href="mailto:prabhatsdp@gmail.com" target="_blank"><img src="https://img.shields.io/badge/Email-%23BB001B.svg?&style=rounded&logo=gmail&logoColor=white" alt="WhatsApp" height="32px"></a>

## Buy Me A Coffee

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/prabhatsdp)

