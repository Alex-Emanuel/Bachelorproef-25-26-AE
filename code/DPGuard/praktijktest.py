from DPGuard import *
from PIL import Image

detector_gpt = DPGuard(binary_model_path="binary_model/binary_rn101_ep6.pth", mllm_model="gpt-4o")
# detector_gemini = DPGuard(binary_model_path="binary_model/binary_rn101_ep6.pth", mllm_model="gemini-2.5-pro-exp-03-25")

# DP praktijktest Demo
# ====================

# Map met screenshots
base_folder = "test_img"

# Ondersteunde extensies
extensions = (".png", ".jpg", ".jpeg", ".webp")

# Door NL en EN lopen
for language in os.listdir(base_folder):
    language_path = os.path.join(base_folder, language)

    if not os.path.isdir(language_path):
        continue

    print("\n==============================")
    print(f"TAAL: {language}")
    print("==============================")

    for filename in os.listdir(language_path):
        if not filename.lower().endswith(extensions):
            continue

        image_path = os.path.join(language_path, filename)

        print("\n--------------------------------")
        print(f"Afbeelding: {filename}")
        print("--------------------------------")

        try:
            # Zorg dat afbeelding RGB is
            img = Image.open(image_path)

            if img.mode != "RGB":
                img = img.convert("RGB")
                img.save(image_path)
                
            result = detector_gpt.detect(image_path)
            print("Resultaat:")
            print(result)

        except Exception as e:
            print("FOUT:")
            print(e)