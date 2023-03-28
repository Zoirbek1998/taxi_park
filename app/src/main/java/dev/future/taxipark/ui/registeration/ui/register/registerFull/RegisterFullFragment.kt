package dev.future.taxipark.ui.registeration.ui.register.registerFull

//class RegisterFullFragment : BaseFragment<FragmentRegisterFullBinding,AuthViewmodel>() {
//
//
//    var builder = MultipartBody.Builder().setType(MultipartBody.FORM)
//    var photoUri : Uri? = null
//    var imagePath =""
//
//    var bool_image_1 = false
//    var bool_image_2 = false
//    var bool_image_3 = false
//    var bool_image_4 = false
//
//
//    override fun injectViewModel() {
//       mViewModel = injectViewModel(viewModelFactory)
//    }
//
//    override fun getViewModelClass(): Class<AuthViewmodel>  = AuthViewmodel::class.java
//
//    override fun onCreate() {
//    }
//
//    override fun init()  = with(binding){
//        addCab()
//
//        binding.imageContainer1.setOnClickListener {
//            bool_image_1 = true
//            showImagePickBottomSheetDialog()
//        }
//        binding.imageContainer2.setOnClickListener {
//            bool_image_2 = true
//            showImagePickBottomSheetDialog()
//        }
//        binding.imageContainer3.setOnClickListener {
//            bool_image_3 = true
//            showImagePickBottomSheetDialog()
//        }
//        binding.imageContainer4.setOnClickListener {
//            bool_image_4 = true
//            showImagePickBottomSheetDialog()
//        }
//    }
//
//    private fun showImagePickBottomSheetDialog() {
//        val bottomSheetDialog = BottomSheetDialog(requireActivity())
//        bottomSheetDialog.setContentView(R.layout.bottomsheet_chooser)
//
//        bottomSheetDialog.findViewById<TextView>(R.id.from_gallery)?.setOnClickListener {
//            getImageGallery.launch("image/*")
//            bottomSheetDialog.dismiss()
//        }
//
//        bottomSheetDialog.findViewById<TextView>(R.id.from_camera)?.setOnClickListener {
//            checkPermission()
//            bottomSheetDialog.dismiss()
//        }
//
//        bottomSheetDialog.show()
//    }
//    fun checkPermission(){
//        Dexter.withContext(requireActivity())
//            .withPermission(android.Manifest.permission.CAMERA)
//            .withListener(object : PermissionListener {
//                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                    photoUri =  FileProvider.getUriForFile(
//                        requireActivity(),
//                        BuildConfig.APPLICATION_ID+ ".provider",
//                        createImageFile()
//                    )
//                    getImageFromCamera.launch(photoUri)
//                }
//
//                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    p0: PermissionRequest?,
//                    p1: PermissionToken?
//                ) {
//                    p1!!.continuePermissionRequest()
//                }
//
//            }).check()
//    }
//
//    private fun createImageFile(): File {
//        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
//        val externalFilesDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)   //tashqi file driktori
//        return File.createTempFile("JPEG_$format",".jpg",externalFilesDir).apply {}
//    }
//    private val getImageFromCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){
//        if (it){
//            if (bool_image_1){
//                binding.camerIcon1.gone()
//                binding.image1.visible()
//                binding.image1.setImageURI(photoUri)
//            }else if (bool_image_2){
//                binding.camerIcon2.gone()
//                binding.image2.visible()
//                binding.image2.setImageURI(photoUri)
//            }else if (bool_image_3){
//                binding.camerIcon3.gone()
//                binding.image3.visible()
//                binding.image3.setImageURI(photoUri)
//            }else if (bool_image_4){
//                binding.camerIcon4.gone()
//                binding.image4.visible()
//                binding.image4.setImageURI(photoUri)
//            }
//            val inputStream = requireActivity().contentResolver?.openInputStream(photoUri!!)  //Oqim kiritish
//            val file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                File(requireActivity().filesDir,"${LocalDateTime.now()}.jpg")
//            }else{
//                File(requireActivity().filesDir,"${Date().time}.jpg")
//            }
//
//            val fileOutputStream = FileOutputStream(file)
//            inputStream?.copyTo(fileOutputStream)
//            inputStream?.close()
//            fileOutputStream.close()
//            imagePath = file.absolutePath
//            Log.e( "addCabIMage", imagePath)
//        }
//    }
//    private val getImageGallery =registerForActivityResult(ActivityResultContracts.GetContent()){
//        it ?: return@registerForActivityResult
//
//        if (bool_image_1){
//            binding.camerIcon1.gone()
//            binding.image1.visible()
//            binding.image1.setImageURI(it)
//        }else if (bool_image_2){
//            binding.camerIcon2.gone()
//            binding.image2.visible()
//            binding.image2.setImageURI(it)
//        }else if (bool_image_3){
//            binding.camerIcon3.gone()
//            binding.image3.visible()
//            binding.image3.setImageURI(it)
//        }else if (bool_image_4){
//            binding.camerIcon4.gone()
//            binding.image4.visible()
//            binding.image4.setImageURI(it)
//        }
//
//        binding.camerIcon1.gone()
//        binding.image1.visible()
//        binding.image1.setImageURI(it)
//        val inputStream = requireActivity().contentResolver?.openInputStream(it)  //Oqim kiritish
//        val file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            File(requireActivity().filesDir,"${LocalDateTime.now()}.jpg")
//        }else{
//            File(requireActivity().filesDir,"${Date().time}.jpg")
//        }
//
//        val fileOutputStream = FileOutputStream(file)
//        inputStream?.copyTo(fileOutputStream)
//        inputStream?.close()
//        fileOutputStream.close()
//        imagePath = file.absolutePath
//        Log.e( "addCabIMage", imagePath)
//    }
//
//    override fun setupViewBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?
//    ): FragmentRegisterFullBinding  = FragmentRegisterFullBinding.inflate(inflater,container,false)
//
//
//    fun getToken(): String {
//        return if (token.isEmpty()) {
//            SaveUserInformation.getAuthInfo().authKey.toString()
//        } else {
//            SaveUserInformation.getAuthInfo().authKey.toString()
//        }
//    }
//    private fun addCab() = with(binding){
//            btnRegister.setOnClickListener {
//                if (validate()){
//
//
//                    builder.addFormDataPart("auth_key", authToken)
//                    builder.addFormDataPart("first_name", edtIsm.text.toString())
//                    builder.addFormDataPart("last_name", edtFamilya.text.toString())
//                    builder.addFormDataPart("middle_name", edtOchstva.text.toString())
//
//                    val file = File(imagePath)
//                    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
//                    val image_1 = MultipartBody.Part.createFormData("dl_photo_first", file.name, requestFile)
//                    val image_2 = MultipartBody.Part.createFormData("dl_photo_second", file.name, requestFile)
//                    val image_3 = MultipartBody.Part.createFormData("vl_photo_first", file.name, requestFile)
//                    val image_4 = MultipartBody.Part.createFormData("vl_photo_second", file.name, requestFile)
//
//                    val authToken2 = RequestBody.create(MediaType.parse("multipart/form-data"), authToken)
//                    val first_name = RequestBody.create(MediaType.parse("multipart/form-data"), edtIsm.text.toString())
//                    val last_name = RequestBody.create(MediaType.parse("multipart/form-data"), edtFamilya.text.toString())
//                    val middle_name = RequestBody.create(MediaType.parse("multipart/form-data"), edtOchstva.text.toString())
//
//
//
//
//
//                    viewModel.fullNurseData(
//                        authToken2,
//                        first_name,
//                        last_name,
//                        middle_name,
//                        image_1,
//                        image_2,
//                        image_3,
//                        image_4,
//                    ).observe(viewLifecycleOwner) { status ->
//                        when (status.status) {
//                            Status.SUCCESS -> status.data.let {
//                                authToken = it?.data?.authKey.toString()
//                                Log.e("addCab: ", it?.toString()!!)
//                                if (it.success) {
//                                    startActivity(Intent(requireContext(), MainActivity::class.java))
//                                    requireActivity().finish()
//
//                                    progress.gone()
//                                    btnRegister.visible()
//                                }
//                            }
//                            Status.ERROR -> status.message.let {
//                                snackBar(it.toString())
//                                progress.gone()
//                                btnRegister.visible()
//                            }
//                            Status.LOADING -> {
//                                progress.visible()
//                                btnRegister.gone()
//                            }
//                        }
//                    }
//
//                }else{
//                    snackBar("Iltimos polyalarni toldiring")
//                }
//            }
//    }
//
//    fun validate(): Boolean {
//
//
//        val name =
//            binding.edtIsm.validate(getString(R.string.eng_kamida4)) { s -> s.length in 4..30 }
//
//        val surname =
//            binding.edtFamilya.validate(getString(R.string.eng_kamida4)) { s -> s.length in 4..30 }
//
//        val middleName =
//            binding.edtOchstva.validate(getString(R.string.eng_kamida4)) { s -> s.length in 4..30 }
//
//        if (!surname) {
//            binding.edtFamilya.requestFocus()
//        } else if (!name) {
//            binding.edtIsm.requestFocus()
//        } else if (!middleName) {
//            binding.edtOchstva.requestFocus()
//        }
//        return name && surname  && middleName
//
//    }
//
//
//
//}