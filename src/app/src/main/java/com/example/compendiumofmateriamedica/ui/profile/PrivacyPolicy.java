package com.example.compendiumofmateriamedica.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.R;
/**
 * @author: Tianhao Shan
 * @datetime: 2024/5
 * @description:
 */
public class PrivacyPolicy extends AppCompatActivity {

    private ImageView back;
    private TextView page_name;
    private TextView page_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_content);

        page_name=findViewById(R.id.page_name);
        page_name.setText("Privacy Policy");

        page_content=findViewById(R.id.page_content);
        page_content.setText("Privacy Policy\n" +
                "\n" +
                "Effective Date: 2024-05-15\n" +
                "\n" +
                "This Privacy Policy describes how G053 (\"we\", \"us\", or \"our\") collects, uses, and shares personal information when you use our Android mobile application (\"App\").\n" +
                "\n" +
                "Information We Collect\n" +
                "\n" +
                "When you use our App, we may collect certain information automatically, including:\n" +
                "\n" +
                "Usage Information: We may collect information about your interactions with the App, such as the features you use, the actions you take, and the frequency and duration of your interactions.\n" +
                "\n" +
                "Device Information: We may collect information about your mobile device, including device type, operating system version, unique device identifiers, and mobile network information.\n" +
                "\n" +
                "Firebase Authentication: We use Firebase Authentication to authenticate users and securely store user passwords and information. Firebase Authentication helps us protect user privacy by ensuring secure authentication and data storage practices.\n" +
                "\n" +
                "How We Use Your Information\n" +
                "\n" +
                "We may use the information we collect for various purposes, including to:\n" +
                "\n" +
                "Provide and maintain the App;\n" +
                "Authenticate users and ensure the security of user accounts;\n" +
                "Improve and optimize the performance of the App;\n" +
                "Analyze trends and user preferences to enhance user experience;\n" +
                "Comply with legal and regulatory requirements.\n" +
                "\n" +
                "Data Security\n" +
                "\n" +
                "We are committed to protecting the security of your personal information. We implement appropriate technical and organizational measures to safeguard your information against unauthorized access, disclosure, alteration, and destruction.\n" +
                "\n" +
                "Third-Party Services\n" +
                "\n" +
                "Our App may contain links to third-party websites or services that are not operated by us. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party websites or services. We encourage you to review the privacy policies of these third parties before providing any personal information.\n" +
                "\n" +
                "Children's Privacy\n" +
                "\n" +
                "Our App is not intended for use by children under the age of 5. We do not knowingly collect personal information from children under 5. If you are a parent or guardian and believe that your child has provided us with personal information, please contact us immediately so that we can take appropriate action.\n" +
                "\n" +
                "Changes to This Privacy Policy\n" +
                "\n" +
                "We may update this Privacy Policy from time to time. You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "If you have any questions or concerns about this Privacy Policy, please contact us at G053@email.com.");

        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}