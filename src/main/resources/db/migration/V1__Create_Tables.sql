CREATE TABLE "user" (
	"id" SERIAL,
	"username" varchar NOT NULL UNIQUE,
	"email" varchar NOT NULL UNIQUE,
	"password" varchar,
	"salt" varchar NOT NULL,
	"password_hash" varchar NOT NULL,
	-- "role" varchar NOT NULL DEFAULT 'user',
	"role" int NOT NULL DEFAULT 1,
	"created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	"updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	CONSTRAINT user_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "category" (
	"id" SERIAL,
	"name" varchar NOT NULL,
	"description" varchar,
	"hierarchy_level" int NOT NULL DEFAULT 1,
	"saved_before" boolean NOT NULL DEFAULT FALSE,
	"created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	"updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	"parent_id" int,
	CONSTRAINT category_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "topic" (
	"id" SERIAL,
	"title" varchar NOT NULL,
	"content" varchar NOT NULL,
	"created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	"updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	"author_id" int NOT NULL,
	"category_id" int NOT NULL,
	CONSTRAINT topic_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "post" (
	"id" SERIAL,
	"content" varchar NOT NULL,
	"hierarchy_level" int NOT NULL DEFAULT 1,
	"saved_before" boolean NOT NULL DEFAULT FALSE,
	"created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	"updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
	"author_id" int NOT NULL,
	"topic_id" int NOT NULL,
	"parent_id" int,
	CONSTRAINT post_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "category_ancestor" (
	"id" SERIAL,
	"category_id" int NOT NULL,
	"ancestor_id" int NOT NULL,
	CONSTRAINT category_ancestor_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "post_ancestor" (
	"id" SERIAL,
	"post_id" int NOT NULL,
	"ancestor_id" int NOT NULL,
	CONSTRAINT post_ancestor_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "category" ADD CONSTRAINT "category_fk0" FOREIGN KEY ("parent_id") REFERENCES "category";

ALTER TABLE "topic" ADD CONSTRAINT "topic_fk0" FOREIGN KEY ("author_id") REFERENCES "user";
ALTER TABLE "topic" ADD CONSTRAINT "topic_fk1" FOREIGN KEY ("category_id") REFERENCES "category";

ALTER TABLE "post" ADD CONSTRAINT "post_fk0" FOREIGN KEY ("author_id") REFERENCES "user";
ALTER TABLE "post" ADD CONSTRAINT "post_fk1" FOREIGN KEY ("topic_id") REFERENCES "topic";
ALTER TABLE "post" ADD CONSTRAINT "post_fk2" FOREIGN KEY ("parent_id") REFERENCES "post";

ALTER TABLE "category_ancestor" ADD CONSTRAINT "category_ancestor_fk0" FOREIGN KEY ("category_id") REFERENCES "category";
ALTER TABLE "category_ancestor" ADD CONSTRAINT "category_ancestor_fk1" FOREIGN KEY ("ancestor_id") REFERENCES "category";

ALTER TABLE "post_ancestor" ADD CONSTRAINT "post_ancestor_fk0" FOREIGN KEY ("post_id") REFERENCES "post";
ALTER TABLE "post_ancestor" ADD CONSTRAINT "post_ancestor_fk1" FOREIGN KEY ("ancestor_id") REFERENCES "post";
